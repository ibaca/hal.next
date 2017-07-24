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
package org.jboss.hal.meta.processing;

import org.jboss.gwt.flow.Control;
import org.jboss.gwt.flow.Function;
import org.jboss.gwt.flow.FunctionContext;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.dmr.Composite;
import org.jboss.hal.dmr.CompositeResult;
import org.jboss.hal.meta.description.ResourceDescriptionRegistry;
import org.jboss.hal.meta.security.SecurityContextRegistry;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Harald Pehl
 */
class RrdFunction implements Function<FunctionContext> {

    @NonNls private static final Logger logger = LoggerFactory.getLogger(RrdFunction.class);

    private final SecurityContextRegistry securityContextRegistry;
    private final ResourceDescriptionRegistry resourceDescriptionRegistry;
    private final Dispatcher dispatcher;
    private final Composite composite;
    private final boolean optional;

    RrdFunction(final SecurityContextRegistry securityContextRegistry,
            final ResourceDescriptionRegistry resourceDescriptionRegistry,
            final Dispatcher dispatcher,
            final Composite composite,
            final boolean optional) {
        this.securityContextRegistry = securityContextRegistry;
        this.resourceDescriptionRegistry = resourceDescriptionRegistry;
        this.dispatcher = dispatcher;
        this.composite = composite;
        this.optional = optional;
    }

    @Override
    public void accept(final Control<FunctionContext> control) {
        dispatcher.executeInFunction(control, composite,
                (CompositeResult compositeResult) -> {
                    try {
                        RrdResult rrdResult = new CompositeRrdParser(composite).parse(compositeResult);
                        rrdResult.securityContexts.forEach((address, securityContext) -> {
                            logger.debug("Add security context for {}", address);
                            securityContextRegistry.add(address, securityContext);
                        });
                        rrdResult.resourceDescriptions.forEach((address, resourceDescription) -> {
                            logger.debug("Add resource description for {}", address);
                            resourceDescriptionRegistry.add(address, resourceDescription);
                        });
                        control.proceed();
                    } catch (ParserException e) {
                        control.getContext().failed(e);
                        control.abort();
                    }
                },
                (operation, failure) -> {
                    if (optional) {
                        logger.debug("Ignore errors on optional resource operation {}", operation.asCli());
                        control.proceed(); // ignore errors on optional resources!
                    } else {
                        control.getContext().failed(failure);
                        control.abort();
                    }
                });
    }
}
