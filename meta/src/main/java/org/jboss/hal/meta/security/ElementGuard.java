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
package org.jboss.hal.meta.security;

import java.util.function.Consumer;
import java.util.function.Predicate;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.hal.resources.UIConstants;

import static org.jboss.hal.resources.CSS.hidden;
import static org.jboss.hal.resources.CSS.rbacHidden;
import static org.jboss.hal.resources.UIConstants.data;

/**
 * Helper class to process elements with constraints in their {@code data-constraint} attributes. Toggles the
 * element's visibility depending on the {@link AuthorisationDecision} result.
 *
 * @author Harald Pehl
 */
public class ElementGuard {

    /**
     * Predicate which returns only visible elements (elements which don't have the CSS class {@link
     * org.jboss.hal.resources.CSS#hidden}).
     * <p>
     * Use this filter to find elements which can be processed by other security related functions such as
     * {@link Toggle}.
     *
     * @author Harald Pehl
     */
    public static class Visible implements Predicate<HTMLElement> {

        @Override
        public boolean test(final HTMLElement element) {
            return element != null && !element.classList.contains(hidden);
        }
    }


    /**
     * Toggle the CSS class {@link org.jboss.hal.resources.CSS#rbacHidden} based on the element's constraints.
     *
     * @author Harald Pehl
     */
    public static class Toggle implements Consumer<HTMLElement> {

        private final AuthorisationDecision authorisationDecision;

        public Toggle(final AuthorisationDecision authorisationDecision) {
            this.authorisationDecision = authorisationDecision;
        }

        @Override
        public void accept(final HTMLElement element) {
            String data = String.valueOf(element.dataset.get(UIConstants.CONSTRAINT));
            if (data != null) {
                Constraints constraints = Constraints.parse(data);
                Elements.toggle(element, rbacHidden, !authorisationDecision.isAllowed(constraints));
            }
        }
    }


    /**
     * Adds the {@link org.jboss.hal.resources.CSS#rbacHidden} CSS class if {@code condition == true}, removes it
     * otherwise.
     */
    public static void toggle(HTMLElement element, boolean condition) {
        if (new Visible().test(element)) {
            Elements.toggle(element, rbacHidden, condition);
        }
    }

    public static void processElements(AuthorisationDecision authorisationDecision, String selector) {
        processElements(authorisationDecision, DomGlobal.document.querySelectorAll(selector));
    }

    public static void processElements(AuthorisationDecision authorisationDecision, HTMLElement element) {
        processElements(authorisationDecision, element.querySelectorAll("[" + data(UIConstants.CONSTRAINT + "]")));
    }

    public static void processElements(AuthorisationDecision authorisationDecision, NodeList<Element> elements) {
        Elements.stream(elements)
                .filter(new Visible()) // prevent that hidden elements become visible by Toggle()
                .forEach(new Toggle(authorisationDecision));
    }
}
