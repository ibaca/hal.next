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
package org.jboss.hal.ballroom.autocomplete;

import elemental.client.Browser;
import elemental.dom.Element;
import elemental.events.Event;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.hal.ballroom.Attachable;
import org.jboss.hal.ballroom.form.AbstractFormItem;
import org.jboss.hal.ballroom.form.FormItem;
import org.jboss.hal.ballroom.form.SuggestHandler;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jsinterop.annotations.JsPackage.GLOBAL;
import static org.jboss.hal.ballroom.form.Form.State.EDITING;
import static org.jboss.hal.resources.CSS.autocompleteSuggestions;

/**
 * Java wrapper for <a href="https://github.com/Pixabay/JavaScript-autoComplete">javascript-auto-complete</a>
 *
 * @author Harald Pehl
 * @see <a href="https://github.com/Pixabay/JavaScript-autoComplete">https://github.com/Pixabay/JavaScript-autoComplete</a>
 */
public class AutoComplete implements SuggestHandler, Attachable {

    @JsType(isNative = true, namespace = GLOBAL, name = "autoComplete")
    static class Bridge {

        @JsConstructor
        @SuppressWarnings("UnusedParameters")
        Bridge(Options options) {
        }

        @JsMethod
        private native void destroy();
    }


    @NonNls static final Logger logger = LoggerFactory.getLogger(AutoComplete.class);

    private FormItem formItem;
    private Bridge bridge;
    private Options options;

    protected void init(Options options) {
        this.options = options;
    }

    @Override
    public void attach() {
        if (bridge == null) {
            options.selector = formItemSelector();
            options.onSelect = (event, item, renderedItem) -> {
                if (formItem() instanceof AbstractFormItem) {
                    ((AbstractFormItem) formItem()).onSuggest(item);
                }
            };
            bridge = new Bridge(options);
        }
    }

    @Override
    public void detach() {
        if (bridge != null) {
            bridge.destroy();
            bridge = null;
        }
    }

    @Override
    public void showAll() {
        Element element = Browser.getDocument().getElementById(formItem().getId(EDITING));
        Browser.getWindow().setTimeout(() -> {
            element.blur();
            triggerEvent(element, Event.KEYUP, "", 0); // to reset 'last_val' in autoComplete.js
            triggerEvent(element, Event.KEYUP, SHOW_ALL_VALUE, SHOW_ALL_VALUE.charAt(0));
            element.focus();
        }, 351); // timeout must be > 350, which is used in autoComplete.js
    }

    private native void triggerEvent(Element element, String type, String key, int keyCode) /*-{
        element.value = key;
        if ($doc.createEvent) {
            event = new Event(type);
            event.keyCode = keyCode;
            event.which = keyCode;
            element.dispatchEvent(event);
        } else {
            event = $doc.createEventObject();
            event.keyCode = keyCode;
            event.which = keyCode;
            element.fireEvent("on" + type, event);
        }
    }-*/;

    @Override
    public void close() {
        Elements.stream(DomGlobal.document.querySelectorAll(autocompleteSuggestions))
                .filter(Elements::isVisible)
                .forEach(element -> Elements.setVisible(element, false));
    }

    @Override
    public void setFormItem(FormItem formItem) {
        this.formItem = formItem;
    }

    private FormItem formItem() {
        if (formItem == null) {
            throw new IllegalStateException(
                    "No form item assigned. Please call AutoComplete.setFormItem(FormItem) before using this as a SuggestHandler.");
        }
        return formItem;
    }

    private String formItemSelector() {
        return "#" + formItem().getId(EDITING);
    }
}
