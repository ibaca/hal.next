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
package org.jboss.hal.client.bootstrap.functions;

import org.jboss.hal.flow.FlowContext;
import org.jboss.hal.flow.Task;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Interface for bootstrap functions. */
public interface BootstrapTask extends Task<FlowContext> {

    @NonNls Logger logger = LoggerFactory.getLogger(BootstrapTask.class);

    @NonNls
    String name();

    default void logStart() {
        logger.info("{}: Start", name());
    }

    default void logDone() {
        logger.info("{}: Done", name());
    }
}
