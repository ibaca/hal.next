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
package org.jboss.hal.resources;

import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import elemental2.dom.HTMLElement;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Previews extends ClientBundleWithLookup {

    // ------------------------------------------------------ access control (rbac)

    @Source("previews/rbac/groups.html")
    ExternalTextResource rbacGroups();

    @Source("previews/rbac/overview.html")
    ExternalTextResource rbacOverview();

    @Source("previews/rbac/roles-domain.html")
    ExternalTextResource rbacRolesDomain();

    @Source("previews/rbac/roles-standalone.html")
    ExternalTextResource rbacRolesStandalone();

    @Source("previews/rbac/users.html")
    ExternalTextResource rbacUsers();


    // ------------------------------------------------------ configuration

    @Source("previews/configuration/batch.html")
    ExternalTextResource configurationBatch();

    @Source("previews/configuration/bean-validation.html")
    ExternalTextResource configurationBeanValidation();

    @Source("previews/configuration/datasources.html")
    ExternalTextResource configurationDatasources();

    @Source("previews/configuration/datasources-drivers.html")
    ExternalTextResource configurationDatasourcesDrivers();

    @Source("previews/configuration/deployment-scanner.html")
    ExternalTextResource configurationDeploymentScanner();

    @Source("previews/configuration/domain.html")
    ExternalTextResource configurationDomain();

    @Source("previews/configuration/ee.html")
    ExternalTextResource configurationEe();

    @Source("previews/configuration/elytron-factories.html")
    ExternalTextResource configurationElytronFactories();

    @Source("previews/configuration/elytron-mappers-decoders.html")
    ExternalTextResource configurationElytronMappersDecoders();

    @Source("previews/configuration/elytron-security-realms.html")
    ExternalTextResource configurationElytronSecurityRealms();

    @Source("previews/configuration/elytron-other-settings.html")
    ExternalTextResource configurationElytronOtherSettings();

    @Source("previews/configuration/ejb3.html")
    ExternalTextResource configurationEjb3();

    @Source("previews/configuration/iiop.html")
    ExternalTextResource configurationIiop();

    @Source("previews/configuration/infinispan.html")
    ExternalTextResource configurationInfinispan();

    @Source("previews/configuration/interfaces.html")
    ExternalTextResource configurationInterfaces();

    @Source("previews/configuration/io.html")
    ExternalTextResource configurationIo();

    @Source("previews/configuration/jax-rs.html")
    ExternalTextResource configurationJaxRs();

    @Source("previews/configuration/jca.html")
    ExternalTextResource configurationJca();

    @Source("previews/configuration/jdr.html")
    ExternalTextResource configurationJdr();

    @Source("previews/configuration/jdbc-drivers.html")
    ExternalTextResource configurationJdbcDrivers();

    @Source("previews/configuration/jgroups.html")
    ExternalTextResource configurationJgroups();

    @Source("previews/configuration/jmx.html")
    ExternalTextResource configurationJmx();

    @Source("previews/configuration/jpa.html")
    ExternalTextResource configurationJpa();

    @Source("previews/configuration/jsf.html")
    ExternalTextResource configurationJsf();

    @Source("previews/configuration/jsr77.html")
    ExternalTextResource configurationJsr77();

    @Source("previews/configuration/logging.html")
    ExternalTextResource configurationLogging();

    @Source("previews/configuration/logging-configuration.html")
    ExternalTextResource configurationLoggingConfiguration();

    @Source("previews/configuration/logging-profiles.html")
    ExternalTextResource configurationLoggingProfiles();

    @Source("previews/configuration/mail.html")
    ExternalTextResource configurationMail();

    @Source("previews/configuration/messaging.html")
    ExternalTextResource configurationMessaging();

    @Source("previews/configuration/messaging-clustering.html")
    ExternalTextResource configurationMessagingClustering();

    @Source("previews/configuration/messaging-connections.html")
    ExternalTextResource configurationMessagingConnections();

    @Source("previews/configuration/messaging-destinations.html")
    ExternalTextResource configurationMessagingDestinations();

    @Source("previews/configuration/messaging-ha-policy.html")
    ExternalTextResource configurationMessagingHaPolicy();

    @Source("previews/configuration/messaging-jms-bridge.html")
    ExternalTextResource configurationMessagingJmsBridge();

    @Source("previews/configuration/messaging-server.html")
    ExternalTextResource configurationMessagingServer();

    @Source("previews/configuration/modcluster.html")
    ExternalTextResource configurationModcluster();

    @Source("previews/configuration/naming.html")
    ExternalTextResource configurationNaming();

    @Source("previews/configuration/paths.html")
    ExternalTextResource configurationPaths();

    @Source("previews/configuration/pojo.html")
    ExternalTextResource configurationPojo();

    @Source("previews/configuration/profiles.html")
    ExternalTextResource configurationProfiles();

    @Source("previews/configuration/remoting.html")
    ExternalTextResource configurationRemoting();

    @Source("previews/configuration/resource-adapters.html")
    ExternalTextResource configurationResourceAdapters();

    @Source("previews/configuration/request-controller.html")
    ExternalTextResource configurationRequestController();

    @Source("previews/configuration/sar.html")
    ExternalTextResource configurationSar();

    @Source("previews/configuration/security-domains.html")
    ExternalTextResource configurationSecurityDomains();

    @Source("previews/configuration/security-elytron.html")
    ExternalTextResource configurationSecurityElytron();

    @Source("previews/configuration/security-manager.html")
    ExternalTextResource configurationSecurityManager();

    @Source("previews/configuration/singleton.html")
    ExternalTextResource configurationSingleton();

    @Source("previews/configuration/socket-bindings.html")
    ExternalTextResource configurationSocketBindings();

    @Source("previews/configuration/standalone.html")
    ExternalTextResource configurationStandalone();

    @Source("previews/configuration/subsystems.html")
    ExternalTextResource configurationSubsystems();

    @Source("previews/configuration/system-properties.html")
    ExternalTextResource configurationSystemProperties();

    @Source("previews/configuration/transactions.html")
    ExternalTextResource configurationTransactions();

    @Source("previews/configuration/undertow.html")
    ExternalTextResource configurationUndertow();

    @Source("previews/configuration/undertow-app-security-domain.html")
    ExternalTextResource configurationUndertowApplicationSecurityDomain();

    @Source("previews/configuration/undertow-buffer-caches.html")
    ExternalTextResource configurationUndertowBufferCaches();

    @Source("previews/configuration/undertow-filters.html")
    ExternalTextResource configurationUndertowFilters();

    @Source("previews/configuration/undertow-handlers.html")
    ExternalTextResource configurationUndertowHandlers();

    @Source("previews/configuration/undertow-server.html")
    ExternalTextResource configurationUndertowServer();

    @Source("previews/configuration/undertow-servlet-container.html")
    ExternalTextResource configurationUndertowServletContainer();

    @Source("previews/configuration/webservices.html")
    ExternalTextResource configurationWebservices();

    @Source("previews/configuration/weld.html")
    ExternalTextResource configurationWeld();


    // ------------------------------------------------------ deployments

    @Source("previews/deployments/content-repository.html")
    ExternalTextResource deploymentsContentRepository();

    @Source("previews/deployments/domain.html")
    ExternalTextResource deploymentsDomain();

    @Source("previews/deployments/server-group.html")
    ExternalTextResource deploymentsServerGroup();

    @Source("previews/deployments/server-groups.html")
    ExternalTextResource deploymentsServerGroups();

    @Source("previews/deployments/standalone.html")
    ExternalTextResource deploymentsStandalone();


    // ------------------------------------------------------ management

    @Source("previews/management/overview.html")
    ExternalTextResource managementOverview();

    @Source("previews/management/extensions.html")
    ExternalTextResource managementExtensions();


    // ------------------------------------------------------ runtime

    @Source("previews/patching/patching.html")
    ExternalTextResource patching();

    // ------------------------------------------------------ runtime

    @Source("previews/runtime/datasources.html")
    ExternalTextResource runtimeDatasources();

    @Source("previews/runtime/domain.html")
    ExternalTextResource runtimeDomain();

    @Source("previews/runtime/hosts.html")
    ExternalTextResource runtimeHosts();

    @Source("previews/runtime/jax-rs.html")
    ExternalTextResource runtimeJaxRs();

    @Source("previews/runtime/jndi.html")
    ExternalTextResource runtimeJndi();

    @Source("previews/runtime/jpa.html")
    ExternalTextResource runtimeJpa();

    @Source("previews/runtime/logfiles.html")
    ExternalTextResource runtimeLogFiles();

    @Source("previews/runtime/messaging-server.html")
    ExternalTextResource runtimeMessagingServer();

    @Source("previews/runtime/server-groups.html")
    ExternalTextResource runtimeServerGroups();

    @Source("previews/runtime/standalone.html")
    ExternalTextResource runtimeStandalone();

    @Source("previews/runtime/topology.html")
    ExternalTextResource runtimeTopology();

    @Source("previews/runtime/worker.html")
    ExternalTextResource runtimeWorker();

    @Source("previews/runtime/web.html")
    ExternalTextResource runtimeWeb();

    // ------------------------------------------------------ helper methods

    @NonNls Logger logger = LoggerFactory.getLogger(Previews.class);

    /**
     * Sets the inner HTML of the specified element to the HTML from the specified resource.
     */
    @SuppressWarnings("DuplicateStringLiteralInspection")
    public static void innerHtml(HTMLElement element, ExternalTextResource resource) {
        if (resource != null) {
            try {
                resource.getText(new ResourceCallback<TextResource>() {
                    @Override
                    public void onError(final ResourceException e) {
                        logger.error("Unable to get preview content from '{}': {}", resource.getName(), e.getMessage());
                    }

                    @Override
                    public void onSuccess(final TextResource textResource) {
                        SafeHtml html = SafeHtmlUtils.fromSafeConstant(textResource.getText());
                        element.innerHTML = html.asString();
                    }
                });
            } catch (ResourceException e) {
                logger.error("Unable to get preview content from '{}': {}", resource.getName(), e.getMessage());
            }
        }
    }
}
