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

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.hal.ballroom;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import elemental.client.Browser;
import elemental2.dom.CSSProperties.WidthUnionType;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.flow.Progress;

import static java.lang.Math.min;
import static java.lang.Math.round;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.gwt.elemento.core.Elements.span;
import static org.jboss.hal.resources.CSS.*;
import static org.jboss.hal.resources.UIConstants.MEDIUM_TIMEOUT;
import static org.jboss.hal.resources.UIConstants.ROLE;

/**
 * @author Harald Pehl
 */
public class ProgressElement implements IsElement, Progress {

    private static final String ARIA_VALUENOW = "aria-valuenow";

    private int value;
    private int max;
    private boolean determinate;
    private final HTMLElement root;
    private final HTMLElement progressBarElement;

    public ProgressElement() {
        value = 0;
        max = 100;
        determinate = true;
        root = div().css(progress, progressXs)
                .add(progressBarElement = div().css(progressBar)
                        .attr(ROLE, "progress-bar") //NON-NLS
                        .aria("valuenow", "0")
                        .aria("valuemin", "0")
                        .aria("valuemax", "100")
                        .add(span().css(srOnly).innerHtml(SafeHtmlUtils.EMPTY_SAFE_HTML))
                        .asElement())
                .asElement();

        Elements.setVisible(root, false);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void reset() {
        reset(0);
    }

    @Override
    public void reset(final int mx) {
        value = 0;
        max = mx;
        determinate = max > 1; // if there's just one step, choose none-determinate state

        if (determinate) {
            progressBarElement.classList.remove(progressBarStriped);
            progressBarElement.classList.remove(active);
            progressBarElement.setAttribute(ARIA_VALUENOW, "0");
            progressBarElement.style.width = WidthUnionType.of("0px"); //NON-NLS
        } else {
            progressBarElement.classList.add(progressBarStriped);
            progressBarElement.classList.add(active);
            progressBarElement.setAttribute(ARIA_VALUENOW, "100");
            progressBarElement.style.width = WidthUnionType.of("100%");
        }
        Elements.setVisible(root, true);
    }

    @Override
    public void tick() {
        if (determinate) {
            if (value < max) {
                value++;
                double percent = min(round(((double) value / (double) max) * 100.0), 100.0);
                progressBarElement.setAttribute(ARIA_VALUENOW, String.valueOf(percent));
                progressBarElement.style.width = WidthUnionType.of(String.valueOf(percent) + "%");
            }
        }
    }

    @Override
    public void finish() {
        // give the user a chance to see that we're finished
        Browser.getWindow().setTimeout(() -> Elements.setVisible(root, false), MEDIUM_TIMEOUT);
    }
}
