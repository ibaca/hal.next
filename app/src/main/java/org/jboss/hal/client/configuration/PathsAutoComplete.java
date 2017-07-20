/*
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.hal.client.configuration;

import java.util.List;

import org.jboss.gwt.flow.Async;
import org.jboss.gwt.flow.FunctionContext;
import org.jboss.gwt.flow.Outcome;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.ballroom.autocomplete.AutoComplete;
import org.jboss.hal.ballroom.autocomplete.NamesResultProcessor;
import org.jboss.hal.ballroom.autocomplete.Options;
import org.jboss.hal.ballroom.autocomplete.OptionsBuilder;
import org.jboss.hal.ballroom.autocomplete.StringRenderer;
import org.jboss.hal.config.Environment;
import org.jboss.hal.core.Core;
import org.jboss.hal.core.runtime.TopologyFunctions;
import org.jboss.hal.core.runtime.server.Server;
import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.Operation;
import org.jboss.hal.dmr.ResourceAddress;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.json.JsonObject;
import org.jboss.hal.meta.StatementContext;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.hal.dmr.ModelDescriptionConstants.CHILD_TYPE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.NAME;
import static org.jboss.hal.dmr.ModelDescriptionConstants.PROFILE_NAME;
import static org.jboss.hal.dmr.ModelDescriptionConstants.READ_CHILDREN_NAMES_OPERATION;

/**
 * Special auto complete class for paths. In standalone mode or in case there's no selected profile the paths are read
 * using {@code :read-children-names(child-type=path)}.
 * <p>
 * In domain mode we try to get the first running server of the selected profile and use {@code
 * /host=foo/server-bar:read-children-names(child-type=path)} or {@code :read-children-names(child-type=path)} in case
 * there's no running server or no selected profile.
 * <p>
 * Since the operation is static it's important to update it when a profile is selected or a server is stopped or
 * started.
 *
 * @author Harald Pehl
 */
public class PathsAutoComplete extends AutoComplete {

    @NonNls private static final Logger logger = LoggerFactory.getLogger(PathsAutoComplete.class);
    private static Operation operation = defaultOperation();

    /**
     * Updates the static operation which is used by this typeahead.
     */
    static void updateOperation(final Environment environment, final Dispatcher dispatcher,
            final StatementContext statementContext) {
        if (environment.isStandalone() || statementContext.selectedProfile() == null) {
            operation = defaultOperation();
        } else {
            Async.single(Progress.NOOP, new FunctionContext(),
                    new Outcome<FunctionContext>() {
                        @Override
                        public void onFailure(final Throwable context) {
                            logger.error("Unable to update operation for paths type-ahead: " +
                                    "Error reading running servers: {}", context.getMessage());
                            operation = defaultOperation();
                        }

                        @Override
                        public void onSuccess(final FunctionContext context) {
                            List<Server> servers = context.get(TopologyFunctions.RUNNING_SERVERS);
                            if (!servers.isEmpty() && servers.get(0).isStarted()) {
                                operation = new Operation.Builder(servers.get(0).getServerAddress(),
                                        READ_CHILDREN_NAMES_OPERATION
                                ).param(CHILD_TYPE, "path").build();
                            } else {
                                operation = defaultOperation();
                            }
                        }
                    },
                    new TopologyFunctions.RunningServersQuery(environment, dispatcher,
                            new ModelNode().set(PROFILE_NAME, statementContext.selectedProfile())));
        }
    }

    private static Operation defaultOperation() {
        return new Operation.Builder(ResourceAddress.root(), READ_CHILDREN_NAMES_OPERATION)
                .param(CHILD_TYPE, "path").build();
    }

    public PathsAutoComplete() {
        Options options = new OptionsBuilder<JsonObject>(
                (query, response) -> Core.INSTANCE.dispatcher().execute(operation,
                        result -> response.response(new NamesResultProcessor().process(query, result))))
                .renderItem(new StringRenderer<>(item -> item.get(NAME).asString()))
                .build();
        init(options);
    }
}
