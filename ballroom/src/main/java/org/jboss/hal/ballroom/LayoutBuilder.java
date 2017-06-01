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
package org.jboss.hal.ballroom;

import elemental2.dom.HTMLDivElement;
import org.jboss.gwt.elemento.core.builder.HtmlContentBuilder;
import org.jboss.hal.resources.CSS;

import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.hal.resources.CSS.*;

/**
 * Builder for bootstrap grid system. Always starts with a initial row. If you need more than one row, use several
 * layout builders and append them to a parent div.
 *
 * @author Harald Pehl
 */
public class LayoutBuilder extends HtmlContentBuilder<HTMLDivElement> {

    public LayoutBuilder() {
        super(div().css(row).asElement());
    }

    @Override
    public LayoutBuilder that() {
        return this;
    }

    /**
     * Adds a column. Columns should contain (sub)headers, elements or tabs.
     */
    public LayoutBuilder column() {
        return column(0, 12);
    }

    public LayoutBuilder column(int columns) {
        return column(0, columns);
    }

    public LayoutBuilder column(int offset, int columns) {
        asElement().appendChild(div().css(rowCss(offset, columns)).asElement());
        return that();
    }

    private String rowCss(int offset, int columns) {
        return offset == 0
                ? CSS.column(columns, columnLg, columnMd, columnSm)
                : offset(offset, columnLg, columnMd, columnSm) + " " + CSS
                .column(columns, columnLg, columnMd, columnSm);
    }
}