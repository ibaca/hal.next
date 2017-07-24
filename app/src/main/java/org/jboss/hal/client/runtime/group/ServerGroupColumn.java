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
package org.jboss.hal.client.runtime.group;

import static org.jboss.hal.core.finder.FinderColumn.RefreshMode.RESTORE_SELECTION;
import static org.jboss.hal.dmr.ModelDescriptionConstants.RELOAD_SERVERS;
import static org.jboss.hal.dmr.ModelDescriptionConstants.RESTART_SERVERS;
import static org.jboss.hal.dmr.ModelDescriptionConstants.RESUME_SERVERS;
import static org.jboss.hal.dmr.ModelDescriptionConstants.SERVER_GROUP;
import static org.jboss.hal.dmr.ModelDescriptionConstants.START_SERVERS;
import static org.jboss.hal.dmr.ModelDescriptionConstants.STOP_SERVERS;
import static org.jboss.hal.dmr.ModelDescriptionConstants.SUSPEND_SERVERS;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import elemental2.dom.HTMLElement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import org.jboss.gwt.flow.Async;
import org.jboss.gwt.flow.FunctionContext;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.config.Environment;
import org.jboss.hal.core.finder.ColumnActionFactory;
import org.jboss.hal.core.finder.Finder;
import org.jboss.hal.core.finder.FinderColumn;
import org.jboss.hal.core.finder.ItemAction;
import org.jboss.hal.core.finder.ItemActionFactory;
import org.jboss.hal.core.finder.ItemDisplay;
import org.jboss.hal.core.finder.ItemMonitor;
import org.jboss.hal.core.mvp.Places;
import org.jboss.hal.core.runtime.TopologyFunctions;
import org.jboss.hal.core.runtime.group.ServerGroup;
import org.jboss.hal.core.runtime.group.ServerGroupActionEvent;
import org.jboss.hal.core.runtime.group.ServerGroupActionEvent.ServerGroupActionHandler;
import org.jboss.hal.core.runtime.group.ServerGroupActions;
import org.jboss.hal.core.runtime.group.ServerGroupResultEvent;
import org.jboss.hal.core.runtime.group.ServerGroupResultEvent.ServerGroupResultHandler;
import org.jboss.hal.core.runtime.group.ServerGroupSelectionEvent;
import org.jboss.hal.core.runtime.server.Server;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.meta.AddressTemplate;
import org.jboss.hal.meta.security.Constraint;
import org.jboss.hal.meta.security.Constraints;
import org.jboss.hal.meta.token.NameTokens;
import org.jboss.hal.resources.Ids;
import org.jboss.hal.resources.Names;
import org.jboss.hal.resources.Resources;
import org.jboss.hal.spi.Column;
import org.jboss.hal.spi.Footer;
import org.jboss.hal.spi.Requires;
import rx.SingleSubscriber;

/**
 * @author Harald Pehl
 */
@Column(Ids.SERVER_GROUP)
@Requires("/server-group=*")
public class ServerGroupColumn extends FinderColumn<ServerGroup>
        implements ServerGroupActionHandler, ServerGroupResultHandler {

    static AddressTemplate serverGroupTemplate(ServerGroup serverGroup) {
        return AddressTemplate.of("/server-group=" + serverGroup.getName());
    }

    @Inject
    public ServerGroupColumn(final Finder finder,
            final Environment environment,
            final Dispatcher dispatcher,
            final EventBus eventBus,
            final @Footer Provider<Progress> progress,
            final ColumnActionFactory columnActionFactory,
            final ItemActionFactory itemActionFactory,
            final ServerGroupActions serverGroupActions,
            final Places places,
            final Resources resources) {

        super(new Builder<ServerGroup>(finder, Ids.SERVER_GROUP, Names.SERVER_GROUP)

                .columnAction(columnActionFactory.add(Ids.SERVER_GROUP_ADD, Names.SERVER_GROUP,
                        AddressTemplate.of("/server-group=*"), Ids::serverGroup))
                .columnAction(columnActionFactory.refresh(Ids.SERVER_GROUP_REFRESH))

                .itemsProvider((context, callback) -> Async.series(progress.get(), new FunctionContext(),
                        new TopologyFunctions.ServerGroupsWithServerConfigs(environment, dispatcher),
                        new TopologyFunctions.ServerGroupsStartedServers(environment, dispatcher)).subscribe(new SingleSubscriber<FunctionContext>() {
                                                    @Override
                                                    public void onError(final Throwable context1) {
                                                        callback.onFailure(context1);
                                                    }

                                                    @Override
                                                    public void onSuccess(final FunctionContext context1) {
                                                        List<ServerGroup> serverGroups = context1.get(TopologyFunctions.SERVER_GROUPS);
                                                        callback.onSuccess(serverGroups);
                                                    }
                                                }))

                .onItemSelect(serverGroup -> eventBus.fireEvent(new ServerGroupSelectionEvent(serverGroup.getName())))
                .onPreview(item -> new ServerGroupPreview(item, places))
                .pinnable()
                .showCount()
                // Unlike other columns the server group column does not have a custom breadcrumb item handler.
                // It makes no sense to replace the server group in a finder path like
                // "server-group => main-server-group / server => server-one / subsystem => logging / log-file => server.log"
                .useFirstActionAsBreadcrumbHandler()
                .withFilter()
        );

        setItemRenderer(item -> new ItemDisplay<ServerGroup>() {
            @Override
            public String getId() {
                return Ids.serverGroup(item.getName());
            }

            @Override
            public String getTitle() {
                return item.getName();
            }

            @Override
            public HTMLElement asElement() {
                return ItemDisplay.withSubtitle(item.getName(), item.getProfile());
            }

            @Override
            public String getFilterData() {
                return String.join(" ", item.getName(), item.getProfile());
            }

            @Override
            public String nextColumn() {
                return Ids.SERVER;
            }

            @Override
            public List<ItemAction<ServerGroup>> actions() {
                PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.SERVER_GROUP_CONFIGURATION)
                        .with(SERVER_GROUP, item.getName()).build();
                List<ItemAction<ServerGroup>> actions = new ArrayList<>();
                actions.add(itemActionFactory.viewAndMonitor(Ids.serverGroup(item.getName()), placeRequest));

                // Order is: reload, restart, suspend, resume, stop, start
                if (item.hasServers(Server::isStarted)) {
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().reload())
                            .handler(serverGroupActions::reload)
                            .constraints(constraints(item, RELOAD_SERVERS))
                            .build());
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().restart())
                            .handler(serverGroupActions::restart)
                            .constraints(constraints(item, RESTART_SERVERS))
                            .build());
                }
                if (item.getServers(Server::isStarted).size() - item.getServers(Server::isSuspended)
                        .size() > 0) {
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().suspend())
                            .handler(serverGroupActions::suspend)
                            .constraints(constraints(item, SUSPEND_SERVERS))
                            .build());
                }
                if (item.hasServers(Server::isSuspended)) {
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().resume())
                            .handler(serverGroupActions::resume)
                            .constraints(constraints(item, RESUME_SERVERS))
                            .build());
                }
                if (item.hasServers(Server::isStarted)) {
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().stop())
                            .handler(serverGroupActions::stop)
                            .constraints(constraints(item, STOP_SERVERS))
                            .build());
                }
                if (item.hasServers(server -> server.isStopped() || server.isFailed())) {
                    actions.add(new ItemAction.Builder<ServerGroup>()
                            .title(resources.constants().start())
                            .handler(serverGroupActions::start)
                            .constraints(constraints(item, START_SERVERS))
                            .build());
                }
                return actions;
            }
        });

        eventBus.addHandler(ServerGroupActionEvent.getType(), this);
        eventBus.addHandler(ServerGroupResultEvent.getType(), this);
    }

    private Constraints constraints(final ServerGroup serverGroup, String operation) {
        return Constraints.or(
                Constraint.executable(AddressTemplate.of("/server-group=*"), operation),
                Constraint.executable(serverGroupTemplate(serverGroup), operation)
        );
    }

    @Override
    public void onServerGroupAction(final ServerGroupActionEvent event) {
        if (isVisible()) {
            event.getServers().forEach(server -> ItemMonitor.startProgress(server.getId()));
        }
    }

    @Override
    public void onServerGroupResult(final ServerGroupResultEvent event) {
        if (isVisible()) {
            event.getServers().forEach(server -> ItemMonitor.stopProgress(server.getId()));
            refresh(RESTORE_SELECTION);
        }
    }
}
