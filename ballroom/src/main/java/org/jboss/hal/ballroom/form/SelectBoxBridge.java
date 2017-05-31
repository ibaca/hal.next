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
package org.jboss.hal.ballroom.form;

import java.util.List;

import elemental.dom.Element;
import elemental.js.events.JsEvent;
import elemental.js.util.JsArrayOf;
import elemental2.dom.HTMLSelectElement;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
import org.jetbrains.annotations.NonNls;

import static jsinterop.annotations.JsPackage.GLOBAL;
import static org.jboss.hal.ballroom.JsHelper.asJsArray;
import static org.jboss.hal.ballroom.JsHelper.asList;
import static org.jboss.hal.dmr.ModelDescriptionConstants.UNDEFINED;
import static org.jboss.hal.resources.UIConstants.OBJECT;

/**
 * @author Harald Pehl
 */
public class SelectBoxBridge {

    @JsType(isNative = true, namespace = GLOBAL, name = OBJECT)
    public static class Options {

        public String noneSelectedText;
    }


    // Helper class to get hold of the default options,
    // since native JS types can neither hold static references nor initializer
    public static class Defaults {

        public static Options get() {
            Options options = new Options();
            options.noneSelectedText = UNDEFINED;
            return options;
        }
    }


    @JsFunction
    @FunctionalInterface
    public interface ChangeListener {

        void onChange(JsEvent event, int index);
    }


    @JsType(isNative = true)
    public static class Single {

        @JsMethod(namespace = GLOBAL, name = "$")
        public native static Single element(HTMLSelectElement element);

        public native String val();

        public native void selectpicker(String method);

        public native void selectpicker(String method, String param);

        public native void on(@NonNls String event, ChangeListener listener);

        @JsOverlay
        public final String getValue() {
            return val();
        }

        @JsOverlay
        public final void setValue(String value) {
            selectpicker(VAL, value);
        }

        @JsOverlay
        public final void onChange(ChangeListener listener) {
            on(CHANGE_EVENT, listener);
        }

        @JsOverlay
        public final void refresh() {
            selectpicker(REFRESH);
        }
    }


    @JsType(isNative = true)
    public static class Multi {

        @JsMethod(namespace = GLOBAL, name = "$")
        public native static Multi element(Element element);

        public native JsArrayOf<String> val();

        public native void selectpicker(String method);

        public native void selectpicker(String method, JsArrayOf<String> param);

        public native void on(@NonNls String event, ChangeListener listener);

        @JsOverlay
        public final List<String> getValue() {
            return asList(val());
        }

        @JsOverlay
        public final void clear() {
            selectpicker(DESELECT_ALL);
        }

        @JsOverlay
        public final void refresh() {
            selectpicker(REFRESH);
        }

        @JsOverlay
        public final void setValue(List<String> value) {
            selectpicker(VAL, asJsArray(value));
        }

        @JsOverlay
        public final void onChange(ChangeListener listener) {
            on(CHANGE_EVENT, listener);
        }
    }


    private final static String VAL = "val";
    private final static String DESELECT_ALL = "deselectAll";
    private final static String REFRESH = "refresh";
    private final static String CHANGE_EVENT = "changed.bs.select";
}
