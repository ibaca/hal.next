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
package org.jboss.hal.client.bootstrap;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import org.jboss.gwt.flow.Async;
import org.jboss.gwt.flow.FunctionContext;
import org.jboss.gwt.flow.Outcome;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.client.bootstrap.endpoint.EndpointManager;
import org.jboss.hal.client.bootstrap.functions.BootstrapFunctions;
import org.jboss.hal.config.Endpoints;
import org.jboss.hal.resources.Names;
import org.jboss.hal.resources.Resources;
import org.jboss.hal.spi.Message;
import org.jboss.hal.spi.MessageEvent;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static elemental2.dom.DomGlobal.document;
import static elemental2.dom.DomGlobal.window;

/**
 * @author Harald Pehl
 */
public class HalBootstrapper implements Bootstrapper {

    @NonNls private static final Logger logger = LoggerFactory.getLogger(HalBootstrapper.class);

    private final EventBus eventBus;
    private final PlaceManager placeManager;
    private final EndpointManager endpointManager;
    private final Endpoints endpoints;
    private final BootstrapFunctions bootstrapFunctions;
    private final Resources resources;

    @Inject
    public HalBootstrapper(final EventBus eventBus,
            final PlaceManager placeManager,
            final EndpointManager endpointManager,
            final Endpoints endpoints,
            final BootstrapFunctions bootstrapFunctions,
            final Resources resources) {
        this.eventBus = eventBus;
        this.placeManager = placeManager;
        this.endpointManager = endpointManager;
        this.endpoints = endpoints;
        this.bootstrapFunctions = bootstrapFunctions;
        this.resources = resources;
    }

    @Override
    public void onBootstrap() {
        // event for users of the JS API
        elemental2.dom.Event event = new elemental2.dom.Event("halReady"); //NON-NLS
        window.dispatchEvent(event);

        Outcome<FunctionContext> outcome = new Outcome<FunctionContext>() {
            @Override
            public void onFailure(final Throwable context) {
                LoadingPanel.get().off();
                logger.error("Bootstrap error: {}", context.getMessage());
                document.body.appendChild(BootstrapFailed.create(context.getMessage(), endpoints).asElement());
            }

            @Override
            public void onSuccess(final FunctionContext context) {
                LoadingPanel.get().off();
                logger.info("Bootstrap finished");
                placeManager.revealCurrentPlace();

                // reset the uncaught exception handler from HalPreBootstrapper
                GWT.setUncaughtExceptionHandler(e -> {
                    String errorMessage = e != null ? e.getMessage() : Names.NOT_AVAILABLE;
                    logger.error("Uncaught exception: {}", errorMessage);
                    placeManager.unlock();
                    MessageEvent.fire(eventBus, Message.error(resources.messages().unknownError(), errorMessage));
                });
            }
        };

        endpointManager.select(() -> {
            LoadingPanel.get().on();
            Async.series(Progress.NOOP, new FunctionContext(), outcome, bootstrapFunctions.functions());
        });
    }
}
