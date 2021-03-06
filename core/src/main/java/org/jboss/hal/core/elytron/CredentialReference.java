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
package org.jboss.hal.core.elytron;

import java.util.function.Supplier;
import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.web.bindery.event.shared.EventBus;
import org.jboss.hal.ballroom.LabelBuilder;
import org.jboss.hal.ballroom.dialog.DialogFactory;
import org.jboss.hal.ballroom.form.Form;
import org.jboss.hal.ballroom.form.FormItem;
import org.jboss.hal.ballroom.form.FormValidation;
import org.jboss.hal.ballroom.form.ValidationResult;
import org.jboss.hal.core.ComplexAttributeOperations;
import org.jboss.hal.core.mbui.dialog.AddResourceDialog;
import org.jboss.hal.core.mbui.form.ModelNodeForm;
import org.jboss.hal.dmr.Composite;
import org.jboss.hal.dmr.CompositeResult;
import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.Operation;
import org.jboss.hal.dmr.ResourceAddress;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.meta.Metadata;
import org.jboss.hal.resources.Ids;
import org.jboss.hal.resources.Names;
import org.jboss.hal.resources.Resources;
import org.jboss.hal.spi.Callback;
import org.jboss.hal.spi.Message;
import org.jboss.hal.spi.MessageEvent;

import static elemental2.dom.DomGlobal.setTimeout;
import static org.jboss.hal.dmr.ModelDescriptionConstants.*;
import static org.jboss.hal.resources.UIConstants.SHORT_TIMEOUT;

/**
 * Provides building blocks for dealing with the {@code credential-reference} complex attribute used in several
 * resources across subsystems.
 */
public class CredentialReference {

    /**
     * Form validation which validates that only one of {@code credential-reference} and {@code <alternativeName>} is
     * given.
     */
    public static class AlternativeValidation<T extends ModelNode> implements FormValidation<T> {

        private final String alternativeName;
        private final Supplier<ModelNode> credentialReferenceValue;
        private final Resources resources;

        public AlternativeValidation(String alternativeName,
                Supplier<ModelNode> credentialReferenceValue, Resources resources) {
            this.alternativeName = alternativeName;
            this.credentialReferenceValue = credentialReferenceValue;
            this.resources = resources;
        }

        @Override
        public ValidationResult validate(Form<T> form) {
            FormItem<String> formItem = form.getFormItem(alternativeName);
            if (formItem != null && !Strings.isNullOrEmpty(formItem.getValue()) && credentialReferenceValue.get()
                    .isDefined()) {
                formItem.showError(resources.messages()
                        .credentialReferenceValidationError(new LabelBuilder().label(alternativeName)));
                return ValidationResult.invalid(resources.messages().credentialReferenceConflict());
            }
            return ValidationResult.OK;
        }
    }


    private static class CrFormValidation implements FormValidation<ModelNode> {

        private final String alternativeName;
        private final Supplier<String> alternativeValue;
        private final Resources resources;

        private CrFormValidation(String alternativeName, Supplier<String> alternativeValue, Resources resources) {
            this.alternativeName = alternativeName;
            this.alternativeValue = alternativeValue;
            this.resources = resources;
        }

        @Override
        public ValidationResult validate(Form<ModelNode> form) {
            if (alternativeName != null && alternativeValue != null && !Strings.isNullOrEmpty(alternativeValue.get())) {
                ValidationResult.invalid(resources.messages()
                        .credentialReferenceValidationError(new LabelBuilder().label(alternativeName)));
            }
            return ValidationResult.OK;
        }
    }


    private final EventBus eventBus;
    private final Dispatcher dispatcher;
    private final ComplexAttributeOperations ca;
    private final Resources resources;

    @Inject
    public CredentialReference(EventBus eventBus, Dispatcher dispatcher, ComplexAttributeOperations ca,
            Resources resources) {
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;
        this.ca = ca;
        this.resources = resources;
    }

    /**
     * Creates a form for the {@code credential-reference} complex attribute of a resource. The form is setup as a
     * singleton form to add, save, reset and remove the complex attribute.
     *
     * @param baseId           base ID used for the generated form and add resource dialog
     * @param metadata         the metadata of the resource which contains the {@code credential-reference}
     *                         attribute
     * @param alternativeName  the name of the alternative attribute
     * @param alternativeValue the value of the alternative attribute
     * @param address          the fully qualified address of the resource used for the CRUD actions
     * @param callback         the callback executed after the {@code credential-reference} attributes has been added,
     *                         saved, reset or removed
     */
    public Form<ModelNode> form(String baseId, Metadata metadata, String alternativeName,
            Supplier<String> alternativeValue, Supplier<ResourceAddress> address, Callback callback) {

        return form(baseId, metadata, CREDENTIAL_REFERENCE, alternativeName, alternativeValue, address, callback);
    }


    /**
     * Creates a form for the {@code credential-reference} complex attribute of a resource. The form is setup as a
     * singleton form to add, save, reset and remove the complex attribute.
     *
     * @param baseId           base ID used for the generated form and add resource dialog
     * @param metadata         the metadata of the resource which contains the {@code credential-reference}
     *                         attribute
     * @param crName           the name of the credential-reference complex attribute
     * @param alternativeName  the name of the alternative attribute
     * @param alternativeValue the value of the alternative attribute
     * @param address          the fully qualified address of the resource used for the CRUD actions
     * @param callback         the callback executed after the {@code credential-reference} attributes has been added,
     *                         saved, reset or removed
     */
    public Form<ModelNode> form(String baseId, Metadata metadata, String crName, String alternativeName,
            Supplier<String> alternativeValue, Supplier<ResourceAddress> address, Callback callback) {

        final String credentialReferenceName = crName == null ? CREDENTIAL_REFERENCE : crName;
        Metadata crMetadata = metadata.forComplexAttribute(credentialReferenceName);
        ModelNodeForm.Builder<ModelNode> formBuilder = new ModelNodeForm.Builder<>(
                Ids.build(baseId, credentialReferenceName, Ids.FORM_SUFFIX), crMetadata)
                .singleton(
                        () -> {
                            ResourceAddress fqAddress = address.get();
                            return fqAddress != null ? new Operation.Builder(address.get(),
                                    READ_ATTRIBUTE_OPERATION)
                                    .param(NAME, credentialReferenceName).build() : null;
                        },
                        () -> {
                            if (alternativeName != null && alternativeValue != null &&
                                    !Strings.isNullOrEmpty(alternativeValue.get())) {
                                String alternativeLabel = new LabelBuilder().label(alternativeName);
                                DialogFactory.showConfirmation(
                                        resources.messages().addResourceTitle(Names.CREDENTIAL_REFERENCE),
                                        resources.messages().credentialReferenceAddConfirmation(alternativeLabel),
                                        () -> setTimeout(
                                                o -> addCredentialReference(baseId, crMetadata, credentialReferenceName,
                                                        alternativeName,
                                                        address, callback),
                                                SHORT_TIMEOUT));
                            } else {
                                addCredentialReference(baseId, crMetadata, credentialReferenceName, null, address,
                                        callback);
                            }
                        })
                .onSave(((f, changedValues) -> {
                    ResourceAddress fqa = address.get();
                    if (fqa != null) {
                        ca.save(credentialReferenceName, Names.CREDENTIAL_REFERENCE, fqa, changedValues,
                                crMetadata, callback);
                    } else {
                        MessageEvent.fire(eventBus,
                                Message.error(resources.messages().credentialReferenceAddressError()));
                    }
                }))
                .prepareReset(f -> {
                    ResourceAddress faAddress = address.get();
                    if (faAddress != null) {
                        ca.reset(credentialReferenceName, Names.CREDENTIAL_REFERENCE, faAddress, crMetadata, f,
                                new Form.FinishReset<ModelNode>(f) {
                                    @Override
                                    public void afterReset(Form<ModelNode> form) {
                                        callback.execute();
                                    }
                                });
                    } else {
                        MessageEvent.fire(eventBus,
                                Message.error(resources.messages().credentialReferenceAddressError()));
                    }
                });

        // some credential-reference attributes are nillable=false, so only nillable=true may be removed
        if (crMetadata.getDescription().get(NILLABLE).asBoolean()) {
            formBuilder.prepareRemove(f -> {
                ResourceAddress fqAddress = address.get();
                if (fqAddress != null) {
                    ca.remove(credentialReferenceName, Names.CREDENTIAL_REFERENCE, fqAddress,
                            new Form.FinishRemove<ModelNode>(f) {
                                @Override
                                public void afterRemove(Form<ModelNode> form) {
                                    callback.execute();
                                }
                            });
                } else {
                    MessageEvent.fire(eventBus,
                            Message.error(resources.messages().credentialReferenceAddressError()));
                }
            });

        }

        Form<ModelNode> form = formBuilder.build();
        form.addFormValidation(new CrFormValidation(alternativeName, alternativeValue, resources));
        return form;
    }

    private void addCredentialReference(String baseId, Metadata crMetadata,
            final String credentialReferenceName, String alternativeName,
            Supplier<ResourceAddress> address, Callback callback) {
        ResourceAddress fqAddress = address.get();
        if (fqAddress != null) {
            String id = Ids.build(baseId, credentialReferenceName, Ids.ADD_SUFFIX);
            Form<ModelNode> form = new ModelNodeForm.Builder<>(id, crMetadata)
                    .addOnly()
                    .include(STORE, ALIAS, TYPE, CLEAR_TEXT)
                    .unsorted()
                    .build();
            new AddResourceDialog(resources.messages().addResourceTitle(Names.CREDENTIAL_REFERENCE),
                    form, (name, model) -> {
                if (alternativeName != null) {
                    Operation undefine = new Operation.Builder(fqAddress, UNDEFINE_ATTRIBUTE_OPERATION)
                            .param(NAME, alternativeName)
                            .build();
                    Operation write = new Operation.Builder(fqAddress, WRITE_ATTRIBUTE_OPERATION)
                            .param(NAME, credentialReferenceName)
                            .param(VALUE, model)
                            .build();
                    dispatcher.execute(new Composite(undefine, write), (CompositeResult result) -> {
                        MessageEvent.fire(eventBus, Message.success(
                                resources.messages().addSingleResourceSuccess(Names.CREDENTIAL_REFERENCE)));
                        callback.execute();
                    });

                } else {
                    ca.add(credentialReferenceName, Names.CREDENTIAL_REFERENCE, fqAddress, model, callback);
                }
            }).show();

        } else {
            MessageEvent.fire(eventBus,
                    Message.error(resources.messages().credentialReferenceAddressError()));
        }
    }
}
