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
package org.jboss.hal.core.finder;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.hal.ballroom.Skeleton.applicationOffset;
import static org.jboss.hal.resources.CSS.column;
import static org.jboss.hal.resources.CSS.finder;
import static org.jboss.hal.resources.CSS.finderPreview;
import static org.jboss.hal.resources.CSS.row;
import static org.jboss.hal.resources.CSS.vh;
import static org.jboss.hal.resources.Ids.FINDER;

import com.google.common.collect.Iterables;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import elemental2.dom.HTMLElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import javax.inject.Provider;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.flow.Async;
import org.jboss.gwt.flow.Control;
import org.jboss.gwt.flow.FunctionContext;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.ballroom.Attachable;
import org.jboss.hal.config.Environment;
import org.jboss.hal.core.finder.ColumnRegistry.LookupCallback;
import org.jboss.hal.core.finder.FinderColumn.RefreshMode;
import org.jboss.hal.meta.security.SecurityContextRegistry;
import org.jboss.hal.resources.Ids;
import org.jboss.hal.spi.Footer;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.SingleSubscriber;

/**
 * The one and only finder which is shared across all different top level categories in HAL. The very same finder
 * instance gets injected into the different top level presenters. Only the columns will change when navigating between
 * the different places
 *
 * @author Harald Pehl
 */
public class Finder implements IsElement, Attachable {

    /**
     * Function used in {@link #select(String, FinderPath, Runnable)} to select one segment in a finder path.
     */
    private class SelectFunction implements Consumer<Control<FunctionContext>> {

        private final FinderSegment segment;

        private SelectFunction(final FinderSegment segment) {
            this.segment = segment;
        }

        @Override
        public void accept(final Control<FunctionContext> control) {
            appendColumn(segment.getColumnId(), new AsyncCallback<FinderColumn>() {
                @Override
                public void onFailure(final Throwable throwable) {
                    logger.error("Error in Finder.SelectFunction: Unable to append column '{}'",
                            segment.getColumnId());
                    control.abort();
                }

                @Override
                public void onSuccess(final FinderColumn finderColumn) {
                    selectItem(finderColumn, control);
                }
            });
        }

        private void selectItem(FinderColumn column, Control<FunctionContext> control) {
            if (column.contains(segment.getItemId())) {
                column.markSelected(segment.getItemId());
                column.row(segment.getItemId()).asElement().scrollIntoView(false);
                updateContext();
                control.getContext().push(column);
                control.proceed();
            } else {
                logger.error("Error in Finder.SelectFunction: Unable to select item '{}' in column '{}'",
                        segment.getItemId(), segment.getColumnId());
                control.abort();
            }
        }
    }

    private class RefreshFunction implements Consumer<Control<FunctionContext>> {

        private final FinderSegment segment;

        private RefreshFunction(final FinderSegment segment) {this.segment = segment;}

        @Override
        public void accept(final Control<FunctionContext> control) {
            FinderColumn column = getColumn(segment.getColumnId());
            if (column != null) {
                // refresh the existing column
                column.refresh(() -> selectItem(column, control));
            } else {
                // append the column
                appendColumn(segment.getColumnId(), new AsyncCallback<FinderColumn>() {
                    @Override
                    public void onFailure(final Throwable throwable) {
                        control.abort();
                    }

                    @Override
                    public void onSuccess(final FinderColumn finderColumn) {
                        selectItem(finderColumn, control);
                    }
                });
            }
        }

        private void selectItem(FinderColumn column, Control<FunctionContext> control) {
            if (column.contains(segment.getItemId())) {
                column.markSelected(segment.getItemId());
                control.getContext().push(column);
                control.proceed();
            } else {
                logger.error("Error in Finder.RefreshFunction: Unable to select item '{}' in column '{}'",
                        segment.getItemId(), segment.getColumnId());
                control.abort();
            }
        }
    }

    static final String DATA_BREADCRUMB = "breadcrumb";
    static final String DATA_FILTER = "filter";
    /**
     * The maximum number of simultaneously visible columns. If there are more columns, the left-most column is hidden.
     * TODO Reduce the number of visible columns if the viewport gets smaller and change col-??-2 to col-??-3
     */
    private static final int MAX_VISIBLE_COLUMNS = 4;

    private static final int MAX_COLUMNS = 12;
    @NonNls private static final Logger logger = LoggerFactory.getLogger(Finder.class);

    private final Environment environment;
    private final EventBus eventBus;
    private final PlaceManager placeManager;
    private final ColumnRegistry columnRegistry;
    private final SecurityContextRegistry securityContextRegistry;
    private final Provider<Progress> progress;
    private final FinderContext context;
    private final LinkedHashMap<String, FinderColumn> columns;
    private final Map<String, String> initialColumnsByToken;
    private final Map<String, PreviewContent> initialPreviewsByToken;
    private final HTMLElement root;
    private final HTMLElement previewColumn;

    // ------------------------------------------------------ ui

    @Inject
    public Finder(final Environment environment,
            final EventBus eventBus,
            final PlaceManager placeManager,
            final ColumnRegistry columnRegistry,
            final SecurityContextRegistry securityContextRegistry,
            @Footer final Provider<Progress> progress) {

        this.environment = environment;
        this.eventBus = eventBus;
        this.placeManager = placeManager;
        this.columnRegistry = columnRegistry;
        this.securityContextRegistry = securityContextRegistry;
        this.progress = progress;

        this.context = new FinderContext();
        this.columns = new LinkedHashMap<>();
        this.initialColumnsByToken = new HashMap<>();
        this.initialPreviewsByToken = new HashMap<>();

        this.root = div().id(FINDER).css(row, finder)
                .add(previewColumn = div()
                        .id(Ids.PREVIEW_ID)
                        .css(finderPreview, column(12))
                        .asElement())
                .asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void attach() {
        root.style.height = vh(applicationOffset());
    }

    private FinderColumn initialColumn() {
        String columnId = initialColumnsByToken.get(context.getToken());
        if (columnId != null) {
            return columns.get(columnId);
        }
        return null;
    }

    private void resizePreview() {
        long visibleColumns = Elements.stream(root).filter(Elements::isVisible).count() - 1;
        int previewSize = MAX_COLUMNS - 2 * min((int) visibleColumns, MAX_VISIBLE_COLUMNS);
        previewColumn.className = finderPreview + " " + column(previewSize);
    }

    // ------------------------------------------------------ internal API

    void appendColumn(String columnId, AsyncCallback<FinderColumn> callback) {
        columnRegistry.lookup(columnId, new LookupCallback() {
            @Override
            public void found(final FinderColumn column) {
                appendColumn(column, callback);
            }

            @Override
            public void error(final String failure) {
                logger.error(failure);
                if (callback != null) {
                    callback.onFailure(new RuntimeException(failure));
                }
            }
        });
    }

    private void appendColumn(FinderColumn<?> column, AsyncCallback<FinderColumn> callback) {
        column.resetSelection();
        column.markHiddenColumns(false);
        Elements.setVisible(column.asElement(), true);

        columns.put(column.getId(), column);
        if (visibleColumns() >= MAX_VISIBLE_COLUMNS) {
            int index = 0;
            int hideUntilHere = columns.size() - MAX_VISIBLE_COLUMNS;
            for (FinderColumn c : columns.values()) {
                Elements.setVisible(c.asElement(), index >= hideUntilHere);
                index++;
            }
            if (hideUntilHere > 0) {
                for (FinderColumn c : columns.values()) {
                    if (Elements.isVisible(c.asElement())) {
                        c.markHiddenColumns(true);
                        break;
                    }
                }
            }
        }

        root.insertBefore(column.asElement(), previewColumn);
        column.attach();
        column.setItems(callback);
        resizePreview();
    }

    private long visibleColumns() {
        return columns.values().stream().filter(column -> Elements.isVisible(column.asElement())).count();
    }

    private void markHiddenColumns() {
        Optional<FinderColumn> hiddenColumn = columns.values().stream()
                .filter(column -> !Elements.isVisible(column.asElement()))
                .findAny();
        if (hiddenColumn.isPresent()) {
            columns.values().stream()
                    .filter(column -> Elements.isVisible(column.asElement()))
                    .findAny()
                    .ifPresent(firstVisibleColumn -> firstVisibleColumn.markHiddenColumns(true));
        }
    }

    void revealHiddenColumns(FinderColumn firstVisibleColumn) {
        // show the last hidden column
        List<FinderColumn> hiddenColumns = columns.values().stream()
                .filter(column -> !Elements.isVisible(column.asElement()))
                .collect(toList());
        if (!hiddenColumns.isEmpty()) {
            Elements.setVisible(Iterables.getLast(hiddenColumns).asElement(), true);
        }
        firstVisibleColumn.markHiddenColumns(false);
        firstVisibleColumn.selectedRow().click();
        markHiddenColumns();
    }

    private void reduceAll() {
        for (Iterator<HTMLElement> iterator = Elements.children(root).iterator(); iterator.hasNext(); ) {
            HTMLElement element = iterator.next();
            if (element == previewColumn) {
                break;
            }
            FinderColumn removeColumn = columns.remove(element.id);
            iterator.remove();
            removeColumn.detach();
        }
    }

    void reduceTo(FinderColumn<?> column) {
        boolean removeFromHere = false;
        for (Iterator<HTMLElement> iterator = Elements.children(root).iterator(); iterator.hasNext(); ) {
            HTMLElement element = iterator.next();
            if (element == column.asElement()) {
                removeFromHere = true;
                continue;
            }
            if (element == previewColumn) {
                break;
            }
            if (removeFromHere) {
                FinderColumn removeColumn = columns.remove(element.id);
                iterator.remove();
                removeColumn.detach();
            }
        }
        Elements.setVisible(column.asElement(), true);
        resizePreview();
    }

    void updateContext() {
        context.getPath().clear();

        for (HTMLElement columnElement : Elements.children(root)) {
            if (columnElement == previewColumn) {
                break;
            }
            String key = columnElement.id;
            FinderColumn<?> column = columns.get(key);
            context.getPath().append(column);
        }
        eventBus.fireEvent(new FinderContextEvent(context));
    }

    void updateHistory() {
        // only finder tokens of the same type please
        PlaceRequest current = placeManager.getCurrentPlaceRequest();
        if (context.getToken().equals(current.getNameToken())) {
            PlaceRequest update = context.toPlaceRequest();
            if (!update.equals(current)) {
                logger.debug("Update history: {}", "#" + context.getToken() +
                        (context.getPath().isEmpty() ? "" : ";path=" + context.getPath()));
                placeManager.updateHistory(update, true);
            }
        }
    }

    void selectColumn(String columnId) {
        FinderColumn finderColumn = columns.get(columnId);
        if (finderColumn != null) {
            finderColumn.asElement().focus();
        }
    }

    void selectPreviousColumn(final String columnId) {
        List<String> columnIds = new ArrayList<>(columns.keySet());
        int index = 0;
        for (String id : columnIds) {
            if (id.equals(columnId)) {
                break;
            }
            index++;
        }
        if (index > 0 && index < columnIds.size()) {
            String previousId = columnIds.get(index - 1);
            selectColumn(previousId);
            FinderColumn previousColumn = columns.get(previousId);
            if (previousColumn != null) {
                FinderRow selectedRow = previousColumn.selectedRow();
                if (selectedRow != null) {
                    selectedRow.click();
                }
            }
        }
    }

    int columns() {
        return columns.size();
    }

    @SuppressWarnings("unchecked")
    void showPreview(PreviewContent preview) {
        Elements.removeChildrenFrom(previewColumn);
        if (preview != null) {
            Iterable<HTMLElement> elements = preview.asElements();
            for (HTMLElement element : elements) {
                previewColumn.appendChild(element);
            }
            preview.attach();
        }
    }

    private void clearPreview() {
        Elements.removeChildrenFrom(previewColumn);
    }

    void showInitialPreview() {
        PreviewContent previewContent = initialPreviewsByToken.get(context.getToken());
        if (previewContent != null) {
            showPreview(previewContent);
        }
    }

    Environment environment() {
        return environment;
    }

    SecurityContextRegistry securityContextRegistry() {
        return securityContextRegistry;
    }

    // ------------------------------------------------------ public API

    /**
     * Resets the finder to its initial state by showing the initial column and preview.
     */
    public void reset(final String token, final String initialColumn, final PreviewContent initialPreview,
            AsyncCallback<FinderColumn> callback) {
        initialColumnsByToken.put(token, initialColumn);
        initialPreviewsByToken.put(token, initialPreview);

        for (FinderColumn column : columns.values()) {
            column.detach();
        }
        columns.clear();
        while (root.firstChild != previewColumn) {
            root.removeChild(root.firstChild);
        }
        context.reset(token);
        appendColumn(initialColumn, callback);
        selectColumn(initialColumn);
        for (FinderColumn column : columns.values()) {
            Elements.setVisible(column.asElement(), true);
            column.markHiddenColumns(false);
        }
        showPreview(initialPreview);
        updateHistory();
    }

    /**
     * Refreshes the current path.
     */
    public void refresh() {
        refresh(getContext().getPath());
    }

    /**
     * Refreshes the specified path.
     * <p>
     * Please note that this might be a complex and long running operation since each segment in the path is turned
     * into a function which reloads and re-selects the items.
     */
    public void refresh(FinderPath path) {
        if (!path.isEmpty()) {
            List<RefreshFunction> tasks = StreamSupport.stream(path.spliterator(), false)
                    .map(RefreshFunction::new).collect(toList());
            Async.series(progress.get(), new FunctionContext(), tasks)
                    .subscribe(new SingleSubscriber<FunctionContext>() {
                        @Override public void onError(final Throwable context1) {}

                        @Override public void onSuccess(final FunctionContext context1) {
                            if (!context1.emptyStack()) {
                                FinderColumn column1 = context1.pop();
                                if (column1.selectedRow() != null) {
                                    column1.selectedRow().click();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * Shows the finder associated with the specified token and selects the columns and items according to the given
     * finder path.
     * <p>
     * Please note that this might be a complex and long running operation since each segment in the path is turned
     * into a function. The function will load and initialize the column and select the item as specified in the
     * segment.
     * <p>
     * If the path is empty, the fallback operation is executed.
     */
    public void select(final String token, final FinderPath path, final Runnable fallback) {
        if (path.isEmpty()) {
            fallback.run();

        } else {
            if (!token.equals(context.getToken())) {
                context.reset(token);
                reduceAll();

            } else {
                // clear the preview right away, otherwise the previous (wrong) preview would be visible until all
                // select functions have been finished
                clearPreview();

                // Find the last common column between the new and the current path
                String match = null;
                FinderPath newPath = path.reversed();
                FinderPath currentPath = context.getPath().reversed();
                for (FinderSegment newSegment : newPath) {
                    for (FinderSegment currentSegment : currentPath) {
                        if (newSegment.getColumnId().equals(currentSegment.getColumnId())) {
                            match = newSegment.getColumnId();
                            break;
                        }
                    }
                    if (match != null) {
                        break;
                    }
                }
                FinderColumn lastCommonColumn = match != null ? columns.get(match) : initialColumn();
                if (lastCommonColumn != null) {
                    reduceTo(lastCommonColumn);
                }
            }

            List<SelectFunction> tasks = StreamSupport.stream(path.spliterator(), false)
                    .map(segment -> new SelectFunction(new FinderSegment(segment.getColumnId(), segment.getItemId())))
                    .collect(toList());
            FunctionContext context = new FunctionContext();
            Async.series(progress.get(), context, tasks)
                    .subscribe(new SingleSubscriber<FunctionContext>() {
                        @Override public void onError(final Throwable error) {
                            if (Finder.this.context.getPath().isEmpty()) {
                                fallback.run();

                            } else if (!context.emptyStack()) {
                                FinderColumn column1 = context.pop();
                                markHiddenColumns(); // only in case of an error!
                                f1nally(column1);
                            }
                        }

                        @Override public void onSuccess(final FunctionContext context1) {
                            f1nally(context1.pop());
                        }

                        @SuppressWarnings("SpellCheckingInspection")
                        private void f1nally(FinderColumn column1) {
                            column1.asElement().focus();
                            column1.refresh(RefreshMode.RESTORE_SELECTION);
                        }
                    });
        }
    }

    public FinderColumn getColumn(String columnId) {
        return columns.get(columnId);
    }

    public FinderContext getContext() {
        return context;
    }
}
