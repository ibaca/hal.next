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
package org.jboss.hal.ballroom.chart;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

/** Entry point to the various JS chart APIs. */
@JsType(namespace = GLOBAL, name = "patternfly", isNative = true)
class Charts {

    @JsProperty(name = "pfDonutTooltipContents")
    static TooltipContentsFn tooltipContents;

    @JsMethod(name = "pfSetDonutChartTitle")
    native static void setDonutChartTitle(String selector, String count, String unit);

    @JsMethod(name = "c3ChartDefaults")
    native static Charts get();

    @JsMethod(name = "getDefaultDonutConfig")
    native Options defaultDonutOptions();

    @JsMethod(name = "getDefaultGroupedBarConfig")
    native Options defaultGroupedBarOptions();
}
