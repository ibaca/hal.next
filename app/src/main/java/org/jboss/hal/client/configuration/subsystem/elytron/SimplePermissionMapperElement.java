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
package org.jboss.hal.client.configuration.subsystem.elytron;

import java.util.List;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.hal.ballroom.Attachable;
import org.jboss.hal.ballroom.Pages;
import org.jboss.hal.ballroom.form.Form;
import org.jboss.hal.ballroom.table.Table;
import org.jboss.hal.core.mbui.form.ModelNodeForm;
import org.jboss.hal.core.mbui.table.ModelNodeTable;
import org.jboss.hal.core.mbui.table.TableButtonFactory;
import org.jboss.hal.core.mvp.HasPresenter;
import org.jboss.hal.dmr.ModelDescriptionConstants;
import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.NamedNode;
import org.jboss.hal.meta.Metadata;
import org.jboss.hal.resources.Ids;
import org.jboss.hal.resources.Names;

import static org.jboss.gwt.elemento.core.Elements.h;
import static org.jboss.gwt.elemento.core.Elements.p;
import static org.jboss.gwt.elemento.core.Elements.section;
import static org.jboss.hal.client.configuration.subsystem.elytron.AddressTemplates.SIMPLE_PERMISSION_MAPPER_TEMPLATE;
import static org.jboss.hal.core.Strings.abbreviateMiddle;
import static org.jboss.hal.dmr.ModelDescriptionConstants.*;
import static org.jboss.hal.dmr.ModelNodeHelper.failSafeList;
import static org.jboss.hal.dmr.ModelNodeHelper.storeIndex;

public class SimplePermissionMapperElement
        implements IsElement<HTMLElement>, Attachable, HasPresenter<MapperDecoderPresenter> {

    // removes the " [ ] from the principals and roles columns values
    private static final String REGEX = "\"|\\[|\\]";

    private final Table<NamedNode> spmTable; // spm = simple permission mapper, the main resource table
    private final Form<NamedNode> spmForm;
    private final Table<ModelNode> pmTable; // pm = permission mappings
    private final Form<ModelNode> pmForm;
    private final Table<ModelNode> permissionsTable;
    private final Form<ModelNode> permissionsForm;
    private final Pages pages;
    private MapperDecoderPresenter presenter;
    private String selectedSimplePermissionMapper;
    private String selectedPermissionMapping;
    private int pmIndex = -1;
    private int permissionsIndex = -1;

    SimplePermissionMapperElement(final Metadata metadata, final TableButtonFactory tableButtonFactory) {

        spmTable = new ModelNodeTable.Builder<NamedNode>(Ids.ELYTRON_SIMPLE_PERMISSION_MAPPER_TABLE, metadata)
                .button(tableButtonFactory.add(Ids.ELYTRON_SIMPLE_PERMISSION_MAPPER_ADD, Names.SIMPLE_PERMISSION_MAPPER,
                        SIMPLE_PERMISSION_MAPPER_TEMPLATE, (name, address) -> presenter.reloadSimplePermissionMapper()))
                .button(tableButtonFactory.remove(Names.SIMPLE_PERMISSION_MAPPER, metadata.getTemplate(),
                        (table) -> table.selectedRow().getName(), () -> presenter.reloadSimplePermissionMapper()))
                .column(NAME, (cell, type, row, meta) -> row.getName())
                .column(Names.PERMISSION_MAPPINGS, this::showPermissionMappings, "15em") //NON-NLS
                .build();

        spmForm = new ModelNodeForm.Builder<NamedNode>(Ids.ELYTRON_SIMPLE_PERMISSION_MAPPER_FORM, metadata)
                .onSave((form, changedValues) -> presenter.saveSimplePermissionMapping(form.getModel().getName(),
                        changedValues))
                .build();

        HTMLElement primarySection = section()
                .add(h(1).textContent(Names.SIMPLE_PERMISSION_MAPPER))
                .add(p().textContent(metadata.getDescription().getDescription()))
                .add(spmTable)
                .add(spmForm)
                .asElement();

        // permission-mappings
        Metadata pmMetadata = metadata.forComplexAttribute(ModelDescriptionConstants.PERMISSION_MAPPINGS);
        pmTable = new ModelNodeTable.Builder<>(Ids.ELYTRON_PERMISSION_MAPPINGS_TABLE, pmMetadata)
                .button(tableButtonFactory.add(pmMetadata.getTemplate(),
                        table -> presenter.addPermissionMappings(selectedSimplePermissionMapper)))
                .button(tableButtonFactory.remove(pmMetadata.getTemplate(),
                        table -> presenter.removePermissionMappings(selectedSimplePermissionMapper, pmIndex)))
                // there are three attributes, none are required=true, so we must display all of them
                .column(PRINCIPALS, (cell, type, row, meta) -> extractValue(row, PRINCIPALS))
                .column(ROLES, (cell, type, row, meta) -> extractValue(row, ROLES))
                .column(PERMISSIONS, (cell, type, row, meta) -> extractPermissionsString(row))
                .column(Names.PERMISSIONS, this::showPermissions, "10em") //NON-NLS
                .build();
        pmForm = new ModelNodeForm.Builder<>(Ids.ELYTRON_PERMISSION_MAPPINGS_FORM, pmMetadata)
                .onSave(((form, changedValues) -> presenter.savePermissionMappings(selectedSimplePermissionMapper,
                        form.getModel().get(HAL_INDEX).asInt(), changedValues)))
                .unsorted()
                .build();
        HTMLElement permissionMappingsSection = section()
                .add(h(1).textContent(Names.PERMISSION_MAPPINGS))
                .add(p().textContent(pmMetadata.getDescription().getDescription()))
                .addAll(pmTable, pmForm)
                .asElement();

        // permissions
        Metadata permissionsMetadata = pmMetadata.forComplexAttribute(PERMISSIONS);
        permissionsTable = new ModelNodeTable.Builder<>(Ids.ELYTRON_PERMISSIONS_TABLE, permissionsMetadata)
                .button(tableButtonFactory.add(permissionsMetadata.getTemplate(),
                        table -> presenter.addPermissions(selectedSimplePermissionMapper, pmIndex)))
                .button(tableButtonFactory.remove(permissionsMetadata.getTemplate(),
                        table -> presenter.removePermissions(selectedSimplePermissionMapper, pmIndex,
                                permissionsIndex)))
                .column(CLASS_NAME)
                .build();
        permissionsForm = new ModelNodeForm.Builder<>(Ids.ELYTRON_PERMISSIONS_FORM, permissionsMetadata)
                .onSave(((form, changedValues) -> presenter.savePermissions(selectedSimplePermissionMapper, pmIndex,
                        form.getModel().get(HAL_INDEX).asInt(), changedValues)))
                .unsorted()
                .build();
        HTMLElement permissionsSection = section()
                .add(h(1).textContent(Names.PERMISSIONS))
                .add(p().textContent(permissionsMetadata.getDescription().getDescription()))
                .addAll(permissionsTable, permissionsForm)
                .asElement();

        pages = new Pages(Ids.ELYTRON_SIMPLE_PERMISSION_MAPPER_PAGE, primarySection);
        pages.addPage(Ids.ELYTRON_SIMPLE_PERMISSION_MAPPER_PAGE, Ids.ELYTRON_PERMISSION_MAPPINGS_PAGE,
                () -> Names.SIMPLE_PERMISSION_MAPPER + ": " + selectedSimplePermissionMapper,
                () -> Names.PERMISSION_MAPPINGS,
                permissionMappingsSection);
        pages.addPage(Ids.ELYTRON_PERMISSION_MAPPINGS_PAGE, Ids.ELYTRON_PERMISSIONS_PAGE,
                () -> Names.PERMISSION_MAPPINGS + ": " + selectedPermissionMapping,
                () -> Names.PERMISSIONS,
                permissionsSection);
    }

    private String extractValue(ModelNode row, String attribute) {
        String value = "";
        if (row.hasDefined(attribute)) {
            value = row.get(attribute).asString().replaceAll(REGEX, "");

        }
        return value;
    }

    @SuppressWarnings("HardCodedStringLiteral")
    private String extractPermissionsString(ModelNode row) {
        String value = "";
        // as the permissions is a list of objects, the concatenated string may be large
        // we shrink it to 120 chars
        if (row.hasDefined(PERMISSIONS)) {
            StringBuilder str = new StringBuilder();
            List<ModelNode> permissions = row.get(PERMISSIONS).asList();
            for (int i = 0; i < permissions.size(); i++) {
                ModelNode node = permissions.get(i);
                String className = node.get(CLASS_NAME).asString();
                str.append(CLASS_NAME).append(": ").append(className);
                if (node.hasDefined(MODULE)) {
                    str.append(", module").append(": ").append(node.get(MODULE).asString());
                }
                if (node.hasDefined("target-name")) {
                    str.append(", target-name").append(": ").append(node.get("target-name").asString());
                }
                if (node.hasDefined(ACTION)) {
                    str.append(", action").append(": ").append(node.get(ACTION).asString());
                }
                if (i + 1 < permissions.size()) {
                    str.append(" | ");
                }
            }
            value = abbreviateMiddle(str.toString(), 120);
        }
        return value;
    }

    private void showPermissionMappings(final NamedNode spmNode) {
        selectedSimplePermissionMapper = spmNode.getName();
        List<ModelNode> permissionMappingsNodes = failSafeList(spmNode, ModelDescriptionConstants.PERMISSION_MAPPINGS);
        storeIndex(permissionMappingsNodes);
        pmTable.update(permissionMappingsNodes, node -> Ids.build(node.get(HAL_INDEX).asString()));
        pages.showPage(Ids.ELYTRON_PERMISSION_MAPPINGS_PAGE);
    }

    private void showPermissions(final ModelNode permissionMappingNode) {
        selectedPermissionMapping = permissionMappingNode.get(HAL_INDEX).asString();
        List<ModelNode> permissionsNodes = failSafeList(permissionMappingNode, ModelDescriptionConstants.PERMISSIONS);
        storeIndex(permissionsNodes);
        permissionsTable.update(permissionsNodes, node -> Ids.build(node.get(CLASS_NAME).asString()));
        pages.showPage(Ids.ELYTRON_PERMISSIONS_PAGE);
    }

    @Override
    public HTMLElement asElement() {
        return pages.asElement();
    }

    @Override
    public void attach() {
        spmTable.attach();
        spmForm.attach();
        spmTable.bindForm(spmForm);

        pmTable.attach();
        pmForm.attach();
        pmTable.bindForm(pmForm);

        permissionsTable.attach();
        permissionsForm.attach();
        permissionsTable.bindForm(permissionsForm);

        pmTable.onSelectionChange(table -> {
            if (table.hasSelection()) {
                pmIndex = table.selectedRow().get(HAL_INDEX).asInt();
            } else {
                pmIndex = -1;
                pmForm.clear();
            }
        });
        permissionsTable.onSelectionChange(table -> {
            if (table.hasSelection()) {
                permissionsIndex = table.selectedRow().get(HAL_INDEX).asInt();
            } else {
                permissionsIndex = -1;
                permissionsForm.clear();
            }
        });
    }

    @Override
    public void setPresenter(final MapperDecoderPresenter presenter) {
        this.presenter = presenter;
    }

    void update(List<NamedNode> nodes) {
        spmForm.clear();
        spmTable.update(nodes);

        if (Ids.ELYTRON_PERMISSION_MAPPINGS_PAGE.equals(pages.getCurrentId())) {
            nodes.stream()
                    .filter(item -> selectedSimplePermissionMapper.equals(item.getName()))
                    .findFirst()
                    .ifPresent(this::showPermissionMappings);
        } else if (Ids.ELYTRON_PERMISSIONS_PAGE.equals(pages.getCurrentId())) {
            nodes.stream()
                    .filter(spmResource -> selectedSimplePermissionMapper.equals(spmResource.getName()))
                    .findFirst()
                    .ifPresent(spmResource -> {
                        List<ModelNode> pmNodes = failSafeList(spmResource, PERMISSION_MAPPINGS);
                        storeIndex(pmNodes);
                        pmForm.clear();
                        pmTable.update(pmNodes,
                                node -> Ids.build(node.get(PRINCIPALS).asString(), node.get(ROLES).asString()));
                        pmNodes.stream()
                                .filter(permission -> selectedPermissionMapping.equals(
                                        permission.get(HAL_INDEX).asString()))
                                .findFirst()
                                .ifPresent(this::showPermissions);
                    });
        }
    }

}
