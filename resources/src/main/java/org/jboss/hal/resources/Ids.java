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
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
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
package org.jboss.hal.resources;

import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.Document;
import org.jetbrains.annotations.NonNls;

import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;
import static org.jboss.hal.resources.Names.PARTICIPANTS;

/**
 * IDs used in HTML elements and across multiple classes. Please add IDs to this interface even if there's already an
 * equivalent or similar constant in {@code ModelDescriptionConstants} (SoC).
 * <p>
 * The IDs defined here are reused by QA. So please make sure that IDs are not spread over the code base but
 * gathered in this interface. This is not always possible - for instance if the ID contains dynamic parts like a
 * resource name or selected server. But IDs which only contain static strings should be part of this interface.
 */
@SuppressWarnings({"DuplicateStringLiteralInspection", "SpellCheckingInspection"})
public interface Ids {

    // ------------------------------------------------------ common suffixes used in IDs below

    String ADD_SUFFIX = "add";
    String COOKIE_PREFIX = "hal-cookie";
    String ENTRY_SUFFIX = "entry";
    String FORM_SUFFIX = "form";
    String PAGE_SUFFIX = "page";
    String REFRESH_SUFFIX = "refresh";
    String STORAGE_PREFIX = "hal-local-storage";
    String TAB_SUFFIX = "tab";
    String TABLE_SUFFIX = "table";


    // ------------------------------------------------------ ids (a-z)
    // Try to compose IDs by making use of the build() method,
    // except the ID needs to be a constant expression (e.g. when used in an annotation).

    String ABOUT_MODAL = "about-modal";
    String ABOUT_MODAL_TITLE = build(ABOUT_MODAL, "title");
    String ACCESS_CONTROL_BROWSE_BY = "access-control-browse-by";
    String ASSIGNMENT = "assignement";
    String ASSIGNMENT_EXCLUDE = build(ASSIGNMENT, "exclude");
    String ASSIGNMENT_INCLUDE = build(ASSIGNMENT, "include");
    String BOOT_ERRORS = "boot-errors";
    String BOOT_ERRORS_ADDRESS_COLUMN = build(BOOT_ERRORS, "address", "column");
    String BOOT_ERRORS_FORM = build(BOOT_ERRORS, FORM_SUFFIX);
    String BOOT_ERRORS_OPERATION_COLUMN = build(BOOT_ERRORS, "operation", "column");
    String BOOT_ERRORS_TABLE = build(BOOT_ERRORS, TABLE_SUFFIX);
    String CACHE_COMPONENT_BACKUP_FOR = "cache-component-backup-for";
    String CACHE_COMPONENT_EVICTION = "cache-component-eviction";
    String CACHE_COMPONENT_EXPIRATION = "cache-component-expiration";
    String CACHE_COMPONENT_LOCKING = "cache-component-locking";
    String CACHE_COMPONENT_PARTITION_HANDLING = "cache-component-partition-handling";
    String CACHE_COMPONENT_STATE_TRANSFER = "cache-component-state-transfer";
    String CACHE_COMPONENT_TRANSACTION = "cache-component-transaction";
    String CACHE_CONTAINER = "cc";
    String CACHE_CONTAINER_ADD = build(CACHE_CONTAINER, ADD_SUFFIX);
    String CACHE_CONTAINER_ENTRY = build(CACHE_CONTAINER, ENTRY_SUFFIX);
    String CACHE_CONTAINER_FORM = build(CACHE_CONTAINER, FORM_SUFFIX);
    String CACHE_CONTAINER_REFRESH = build(CACHE_CONTAINER, REFRESH_SUFFIX);
    String CACHE_CONTAINER_THREAD_POOL_ASYNC_OPERATIONS = build(CACHE_CONTAINER, "thread-pool", "async-operations");
    String CACHE_CONTAINER_THREAD_POOL_EXPIRATION = build(CACHE_CONTAINER, "thread-pool", "expiration");
    String CACHE_CONTAINER_THREAD_POOL_LISTENER = build(CACHE_CONTAINER, "thread-pool", "listener");
    String CACHE_CONTAINER_THREAD_POOL_PERSISTENCE = build(CACHE_CONTAINER, "thread-pool", "persistence");
    String CACHE_CONTAINER_THREAD_POOL_REMOTE_COMMAND = build(CACHE_CONTAINER, "thread-pool", "remote-command");
    String CACHE_CONTAINER_THREAD_POOL_STATE_TRANSFER = build(CACHE_CONTAINER, "thread-pool", "state-transfer");
    String CACHE_CONTAINER_THREAD_POOL_TRANSPORT = build(CACHE_CONTAINER, "thread-pool", "transport");
    String CACHE_CONTAINER_THREAD_POOLS_ENTRY = build(CACHE_CONTAINER, "thread-pool", "async-operations");
    String CACHE_CONTAINER_TRANSPORT_ENTRY = build(CACHE_CONTAINER, "transport", ENTRY_SUFFIX);
    String CACHE_CONTAINER_TRANSPORT_FORM = build(CACHE_CONTAINER, "transport", FORM_SUFFIX);
    String CACHE_STORE_BINARY_JDBC = "cache-store-binary-jdbc";
    String CACHE_STORE_BINARY_TABLE = "binary-table";
    String CACHE_STORE_CUSTOM = "cache-store-custom";
    String CACHE_STORE_FILE = "cache-store-file";
    String CACHE_STORE_JDBC = "cache-store-jdbc";
    String CACHE_STORE_MIXED_JDBC = "cache-store-mixed-jdbc";
    String CACHE_STORE_REMOTE = "cache-store-remote";
    String CACHE_STORE_STRING_TABLE = "string-table";
    String CACHE_STORE_WRITE_BEHIND = "behind";
    String CACHE_STORE_WRITE_THROUGH = "write";
    String CONFIGURATION = "configuration";
    String CONFIGURATION_CHANGES = "configuration-changes";
    String CONFIGURATION_SUBSYSTEM = "css";
    String CONTENT = "content";
    String CONTENT_ADD = build(CONTENT, ADD_SUFFIX);
    String CONTENT_ADD_ACTIONS = build(CONTENT, "add-actions");
    String CONTENT_EDITOR = build(CONTENT, "editor");
    String CONTENT_REFRESH = build(CONTENT, REFRESH_SUFFIX);
    String CONTENT_SEARCH = build(CONTENT, "search");
    String CONTENT_TAB = build(CONTENT, TAB_SUFFIX);
    String CONTENT_TREE = build(CONTENT, "tree");
    String CONTENT_TREE_ROOT = build(CONTENT, "tree-root");
    String CONTENT_TREE_SEARCH = build(CONTENT, "tree", "search");
    String CONTENT_UNMANAGED_ADD = build(CONTENT, "unmanaged", ADD_SUFFIX);
    String DATA_SOURCE_CONFIGURATION = "ds-configuration";
    String DATA_SOURCE_ADD = build(DATA_SOURCE_CONFIGURATION, ADD_SUFFIX);
    String DATA_SOURCE_ADD_ACTIONS = build(DATA_SOURCE_CONFIGURATION, "add-actions");
    String DATA_SOURCE_CONNECTION_FORM = build(DATA_SOURCE_CONFIGURATION, "connection", FORM_SUFFIX);
    String DATA_SOURCE_DRIVER = "data-source-driver";
    String DATA_SOURCE_DRIVER_FORM = build(DATA_SOURCE_CONFIGURATION, "driver", FORM_SUFFIX);
    String DATA_SOURCE_FORM = build(DATA_SOURCE_CONFIGURATION, FORM_SUFFIX);
    String DATA_SOURCE_NAMES_FORM = build(DATA_SOURCE_CONFIGURATION, "names", FORM_SUFFIX);
    String DATA_SOURCE_PROPERTIES_FORM = build(DATA_SOURCE_CONFIGURATION, "properties", FORM_SUFFIX);
    String DATA_SOURCE_REFRESH = build(DATA_SOURCE_CONFIGURATION, REFRESH_SUFFIX);
    String DATA_SOURCE_REVIEW_FORM = build(DATA_SOURCE_CONFIGURATION, "review", FORM_SUFFIX);
    String DATA_SOURCE_RUNTIME = "ds-runtime";
    String DATA_SOURCE_RUNTIME_JDBC_FORM = build(DATA_SOURCE_RUNTIME, "jdbc", FORM_SUFFIX);
    String DATA_SOURCE_RUNTIME_JDBC_TAB = build(DATA_SOURCE_RUNTIME, "jdbc", TAB_SUFFIX);
    String DATA_SOURCE_RUNTIME_POOL_FORM = build(DATA_SOURCE_RUNTIME, "pool", FORM_SUFFIX);
    String DATA_SOURCE_RUNTIME_POOL_TAB = build(DATA_SOURCE_RUNTIME, "pool", TAB_SUFFIX);
    String DEPLOYMENT = "deployment";
    String DEPLOYMENT_ADD_ACTIONS = build(DEPLOYMENT, "add-actions");
    String DEPLOYMENT_BROWSE_BY = "deployment-browse-by";
    String DEPLOYMENT_REFRESH = build(DEPLOYMENT, REFRESH_SUFFIX);
    String DEPLOYMENT_SERVER_GROUP = "deployment-sg";
    String DEPLOYMENT_TAB = build(DEPLOYMENT, TAB_SUFFIX);
    String DEPLOYMENT_UNMANAGED_ADD = build(DEPLOYMENT, "unmanaged", ADD_SUFFIX);
    String DEPLOYMENT_UPLOAD = build(DEPLOYMENT, "upload");
    String DISTRIBUTED_CACHE = "distributed-cache";
    String DOMAIN_BROWSE_BY = "domain-browse-by";
    String DRAG_AND_DROP_DEPLOYMENT = "drag-and-drop-deployment";
    String EE = "ee";
    String EE_ATTRIBUTES_ENTRY = build(EE, "attributes", ENTRY_SUFFIX);
    String EE_ATTRIBUTES_FORM = build(EE, "attributes", FORM_SUFFIX);
    String EE_CONTEXT_SERVICE = build(EE, "service", "context-service");
    String EE_DEFAULT_BINDINGS_ENTRY = build(EE, "default-bindings", ENTRY_SUFFIX);
    String EE_DEFAULT_BINDINGS_FORM = build(EE, "default-bindings", FORM_SUFFIX);
    String EE_GLOBAL_MODULES_ENTRY = build(EE, "global-modules", ENTRY_SUFFIX);
    String EE_GLOBAL_MODULES_FORM = build(EE, "global-modules", FORM_SUFFIX);
    String EE_GLOBAL_MODULES_TABLE = build(EE, "global-modules", TABLE_SUFFIX);
    String EE_MANAGED_EXECUTOR = build(EE, "service", "executor");
    String EE_MANAGED_EXECUTOR_SCHEDULED = build(EE, "service", "scheduled-executor");
    String EE_MANAGED_THREAD_FACTORY = build(EE, "service", "thread-factories");
    String EE_SERVICES_ENTRY = build(Ids.EE, "services", ENTRY_SUFFIX);
    String EJB3 = "ejb3";
    String EJB3_APPLICATION_SECURITY_DOMAIN = build(EJB3, "app-security-domain");
    String EJB3_APPLICATION_SECURITY_DOMAIN_ADD = build(EJB3_APPLICATION_SECURITY_DOMAIN, ADD_SUFFIX);
    String EJB3_APPLICATION_SECURITY_DOMAIN_ENTRY = build(EJB3_APPLICATION_SECURITY_DOMAIN, ENTRY_SUFFIX);
    String EJB3_APPLICATION_SECURITY_DOMAIN_FORM = build(EJB3_APPLICATION_SECURITY_DOMAIN, FORM_SUFFIX);
    String EJB3_APPLICATION_SECURITY_DOMAIN_TABLE = build(EJB3_APPLICATION_SECURITY_DOMAIN, TABLE_SUFFIX);
    String EJB3_DEPLOYMENT = build(EJB3, "deployment");
    String ELYTRON = "elytron";
    String ELYTRON_ADD_PREFIX_ROLE_MAPPER = build(ELYTRON, "add-prefix-role-mapper");
    String ELYTRON_ADD_SUFFIX_ROLE_MAPPER = build(ELYTRON, "add-suffix-role-mapper");
    String ELYTRON_AGGREGATE_HTTP_SERVER_MECHANISM_FACTORY = build(ELYTRON, "aggregate-http-server-mechanism-factory");
    String ELYTRON_AGGREGATE_PRINCIPAL_DECODER = build(ELYTRON, "aggregate-principal-decoder");
    String ELYTRON_AGGREGATE_PRINCIPAL_TRANSFORMER = build(ELYTRON, "aggregate-principal-transformer");
    String ELYTRON_AGGREGATE_PROVIDERS = build(ELYTRON, "aggregate-providers");
    String ELYTRON_AGGREGATE_REALM = build(ELYTRON, "aggregate-realm");
    String ELYTRON_AGGREGATE_ROLE_MAPPER = build(ELYTRON, "aggregate-role-mapper");
    String ELYTRON_AGGREGATE_SASL_SERVER_FACTORY = build(ELYTRON, "aggregate-sasl-server-factory");
    String ELYTRON_AGGREGATE_SECURITY_EVENT_LISTENER = build(ELYTRON, "aggregate-security-event-listener");
    String ELYTRON_AUTHENTICATION_CONFIGURATION = build(ELYTRON, "authentication-configuration");
    String ELYTRON_AUTHENTICATION_CONTEXT = build(ELYTRON, "authentication-context");
    String ELYTRON_CACHING_REALM = build(ELYTRON, "caching-realm");
    String ELYTRON_CHAINED_PRINCIPAL_TRANSFORMER = build(ELYTRON, "chained-principal-transformer");
    String ELYTRON_CLIENT_SSL_CONTEXT = build(ELYTRON, "client-ssl-context");
    String ELYTRON_CONCATENATING_PRINCIPAL_DECODER = build(ELYTRON, "concatenating-principal-decoder");
    String ELYTRON_CONFIGURABLE_HTTP_SERVER_MECHANISM_FACTORY = build(ELYTRON,
            "configurable-http-server-mechanism-factory");
    String ELYTRON_CONFIGURABLE_SASL_SERVER_FACTORY = build(ELYTRON, "configurable-sasl-server-factory");
    String ELYTRON_CONSTANT_PERMISSION_MAPPER = build(ELYTRON, "constant-permission-mapper");
    String ELYTRON_CONSTANT_PRINCIPAL_DECODER = build(ELYTRON, "constant-principal-decoder");
    String ELYTRON_CONSTANT_PRINCIPAL_TRANSFORMER = build(ELYTRON, "constant-principal-transformer");
    String ELYTRON_CONSTANT_REALM_MAPPER = build(ELYTRON, "constant-realm-mapper");
    String ELYTRON_CONSTANT_ROLE_MAPPER = build(ELYTRON, "constant-role-mapper");
    String ELYTRON_CREDENTIAL_STORE = build(ELYTRON, "credential-store");
    String ELYTRON_CUSTOM_CREDENTIAL_SECURITY_FACTORY = build(ELYTRON, "custom-credential-security-factory");
    String ELYTRON_CUSTOM_MODIFIABLE_REALM = build(ELYTRON, "custom-modifiable-realm");
    String ELYTRON_CUSTOM_PERMISSION_MAPPER = build(ELYTRON, "custom-permission-mapper");
    String ELYTRON_CUSTOM_POLICY = build(ELYTRON, "custom-policy");
    String ELYTRON_CUSTOM_POLICY_ADD = build(ELYTRON_CUSTOM_POLICY, ADD_SUFFIX);
    String ELYTRON_CUSTOM_POLICY_FORM = build(ELYTRON_CUSTOM_POLICY, FORM_SUFFIX);
    String ELYTRON_CUSTOM_POLICY_PAGE = build(ELYTRON_CUSTOM_POLICY, PAGE_SUFFIX);
    String ELYTRON_CUSTOM_POLICY_TABLE = build(ELYTRON_CUSTOM_POLICY, TABLE_SUFFIX);
    String ELYTRON_CUSTOM_PRINCIPAL_DECODER = build(ELYTRON, "custom-principal-decoder");
    String ELYTRON_CUSTOM_PRINCIPAL_TRANSFORMER = build(ELYTRON, "custom-principal-transformer");
    String ELYTRON_CUSTOM_REALM = build(ELYTRON, "custom-realm");
    String ELYTRON_CUSTOM_REALM_MAPPER = build(ELYTRON, "custom-realm-mapper");
    String ELYTRON_CUSTOM_ROLE_DECODER = build(ELYTRON, "custom-role-decoder");
    String ELYTRON_CUSTOM_ROLE_MAPPER = build(ELYTRON, "custom-role-mapper");
    String ELYTRON_DIR_CONTEXT = build(ELYTRON, "dir-context");
    String ELYTRON_FILE_AUDIT_LOG = build(ELYTRON, "file-audit-log");
    String ELYTRON_FILESYSTEM_REALM = build(ELYTRON, "filesystem-realm");
    String ELYTRON_FILTERING_KEY_STORE = build(ELYTRON, "filtering-key-store");
    String ELYTRON_HTTP_AUTHENTICATION_FACTORY = build(ELYTRON, "http-authentication-factory");
    String ELYTRON_IDENTITY_REALM = build(ELYTRON, "identity-realm");
    String ELYTRON_JACC_POLICY = build(ELYTRON, "jacc-policy");
    String ELYTRON_JACC_POLICY_ADD = build(ELYTRON_JACC_POLICY, ADD_SUFFIX);
    String ELYTRON_JACC_POLICY_FORM = build(ELYTRON_JACC_POLICY, FORM_SUFFIX);
    String ELYTRON_JACC_POLICY_PAGE = build(ELYTRON_JACC_POLICY, PAGE_SUFFIX);
    String ELYTRON_JACC_POLICY_TABLE = build(ELYTRON_JACC_POLICY, TABLE_SUFFIX);
    String ELYTRON_JDBC_REALM = build(ELYTRON, "jdbc-realm");
    String ELYTRON_KERBEROS_SECURITY_FACTORY = build(ELYTRON, "kerberos-security-factory");
    String ELYTRON_KEY_MANAGER = build(ELYTRON, "key-manager");
    String ELYTRON_KEY_STORE = build(ELYTRON, "key-store");
    String ELYTRON_KEY_STORE_REALM = build(ELYTRON, "key-store-realm");
    String ELYTRON_LDAP_KEY_STORE = build(ELYTRON, "ldap-key-store");
    String ELYTRON_LDAP_REALM = build(ELYTRON, "ldap-realm");
    String ELYTRON_LOGICAL_PERMISSION_MAPPER = build(ELYTRON, "logical-permission-mapper");
    String ELYTRON_LOGICAL_ROLE_MAPPER = build(ELYTRON, "logical-role-mapper");
    String ELYTRON_MAPPED_REGEX_REALM_MAPPER = build(ELYTRON, "mapped-regex-realm-mapper");
    String ELYTRON_MECHANISM_PROVIDER_FILTERING_SASL_SERVER_FACTORY = build(ELYTRON,
            "mechanism-provider-filtering-sasl-server-factory");
    String ELYTRON_PERIODIC_ROTATING_FILE_AUDIT_LOG = build(ELYTRON, "periodic-rotating-file-audit-log");
    String ELYTRON_PERMISSIONS = build(ELYTRON, "permissions");
    String ELYTRON_PERMISSIONS_ADD = build(ELYTRON_PERMISSIONS, ADD_SUFFIX);
    String ELYTRON_PERMISSIONS_FORM = build(ELYTRON_PERMISSIONS, FORM_SUFFIX);
    String ELYTRON_PERMISSIONS_PAGE = build(ELYTRON_PERMISSIONS, PAGE_SUFFIX);
    String ELYTRON_PERMISSIONS_TABLE = build(ELYTRON_PERMISSIONS, TABLE_SUFFIX);
    String ELYTRON_PERMISSION_MAPPINGS = build(ELYTRON, "permission-mappings");
    String ELYTRON_PERMISSION_MAPPINGS_ADD = build(ELYTRON_PERMISSION_MAPPINGS, ADD_SUFFIX);
    String ELYTRON_PERMISSION_MAPPINGS_FORM = build(ELYTRON_PERMISSION_MAPPINGS, FORM_SUFFIX);
    String ELYTRON_PERMISSION_MAPPINGS_PAGE = build(ELYTRON_PERMISSION_MAPPINGS, PAGE_SUFFIX);
    String ELYTRON_PERMISSION_MAPPINGS_TABLE = build(ELYTRON_PERMISSION_MAPPINGS, TABLE_SUFFIX);
    String ELYTRON_POLICY = build(ELYTRON, "policy");
    String ELYTRON_POLICY_FORM = build(ELYTRON, "policy", FORM_SUFFIX);
    String ELYTRON_POLICY_PAGE = build(ELYTRON, "policy", PAGE_SUFFIX);
    String ELYTRON_POLICY_TABLE = build(ELYTRON, "policy", TABLE_SUFFIX);
    String ELYTRON_PROPERTIES_REALM = build(ELYTRON, "properties-realm");
    String ELYTRON_PROVIDER_HTTP_SERVER_MECHANISM_FACTORY = build(ELYTRON, "provider-http-server-mechanism-factory");
    String ELYTRON_PROVIDER_LOADER = build(ELYTRON, "provider-loader");
    String ELYTRON_PROVIDER_SASL_SERVER_FACTORY = build(ELYTRON, "provider-sasl-server-factory");
    String ELYTRON_REGEX_PRINCIPAL_TRANSFORMER = build(ELYTRON, "regex-principal-transformer");
    String ELYTRON_REGEX_VALIDATING_PRINCIPAL_TRANSFORMER = build(ELYTRON, "regex-validating-principal-transformer");
    String ELYTRON_SASL_AUTHENTICATION_FACTORY = build(ELYTRON, "sasl-authentication-factory");
    String ELYTRON_SECURITY_DOMAIN = build(ELYTRON, "security-domain");
    String ELYTRON_SERVER_SSL_CONTEXT = build(ELYTRON, "server-ssl-context");
    String ELYTRON_SERVICE_LOADER_HTTP_SERVER_MECHANISM_FACTORY = build(ELYTRON,
            "service-loader-http-server-mechanism-factory");
    String ELYTRON_SERVICE_LOADER_SASL_SERVER_FACTORY = build(ELYTRON, "service-loader-sasl-server-factory");
    String ELYTRON_SIMPLE_PERMISSION_MAPPER = build(ELYTRON, "simple-permission-mapper");
    String ELYTRON_SIMPLE_PERMISSION_MAPPER_ADD = build(ELYTRON_SIMPLE_PERMISSION_MAPPER, ADD_SUFFIX);
    String ELYTRON_SIMPLE_PERMISSION_MAPPER_FORM = build(ELYTRON_SIMPLE_PERMISSION_MAPPER, FORM_SUFFIX);
    String ELYTRON_SIMPLE_PERMISSION_MAPPER_PAGE = build(ELYTRON_SIMPLE_PERMISSION_MAPPER, PAGE_SUFFIX);
    String ELYTRON_SIMPLE_PERMISSION_MAPPER_TABLE = build(ELYTRON_SIMPLE_PERMISSION_MAPPER, TABLE_SUFFIX);
    String ELYTRON_SIMPLE_REGEX_REALM_MAPPER = build(ELYTRON, "single-regex-realm-mapper");
    String ELYTRON_SIMPLE_ROLE_DECODER = build(ELYTRON, "simple-role-decoder");
    String ELYTRON_SIZE_ROTATING_FILE_AUDIT_LOG = build(ELYTRON, "size-rotating-file-audit-log");
    String ELYTRON_SYSLOG_AUDIT_LOG = build(ELYTRON, "syslog-audit-log");
    String ELYTRON_TOKEN_REALM = build(ELYTRON, "token-realm");
    String ELYTRON_TRUST_MANAGER = build(ELYTRON, "trust-manager");
    String ELYTRON_X500_ATTRIBUTE_PRINCIPAL_DECODER = build(ELYTRON, "x500-attribute-principal-decoder");
    String ENDPOINT = "endpoint";
    String ENDPOINT_ADD = build(ENDPOINT, "add");
    String ENDPOINT_PING = build(ENDPOINT, "ping");
    String ENDPOINT_REFRESH = build(ENDPOINT, REFRESH_SUFFIX);
    String ENDPOINT_SELECT = build(ENDPOINT, "select");
    String ENDPOINT_STORAGE = build(STORAGE_PREFIX, ENDPOINT);
    String EXTENSION = "extension";
    String EXTENSION_ADD = build(EXTENSION, ADD_SUFFIX);
    String EXTENSION_REVIEW_FORM = build(EXTENSION, "review", FORM_SUFFIX);
    String EXTENSION_STORAGE = build(STORAGE_PREFIX, EXTENSION);
    String EXTENSION_URL_FORM = build(EXTENSION, "url", FORM_SUFFIX);
    String FINDER = "hal-finder";
    String FOOTER_EXTENSIONS = "footer-extensions";
    String FOOTER_EXTENSIONS_DROPDOWN = build(FOOTER_EXTENSIONS, "dropdown");
    String GROUP = "group";
    String HAL_MODAL = "hal-modal";
    String HAL_MODAL_TITLE = build(HAL_MODAL, "title");
    String HAL_WIZARD = "hal-wizard";
    String HAL_WIZARD_TITLE = build(HAL_WIZARD, "title");
    String HEADER = "header";
    String HEADER_CONNECTED_TO = build(HEADER, "connected-to");
    String HEADER_EXTENSIONS = build(HEADER, "extensions");
    String HEADER_EXTENSIONS_DROPDOWN = build(HEADER_EXTENSIONS, "dropdown");
    String HEADER_USERNAME = build(HEADER, "username");
    String HOMEPAGE = "homepage";
    String HOMEPAGE_ACCESS_CONTROL_SECTION = build(HOMEPAGE, "access-control-section");
    String HOMEPAGE_CONFIGURATION_SECTION = build(HOMEPAGE, "configuration-section");
    String HOMEPAGE_DEPLOYMENTS_SECTION = build(HOMEPAGE, "deployments-section");
    String HOMEPAGE_PATCHING_SECTION = build(HOMEPAGE, "patching-section");
    String HOMEPAGE_RUNTIME_MONITOR_SECTION = build(HOMEPAGE, "runtime-monitor-section");
    String HOMEPAGE_RUNTIME_SECTION = build(HOMEPAGE, "runtime-section");
    String HOMEPAGE_RUNTIME_SERVER_GROUP_SECTION = build(HOMEPAGE, "runtime-server-group-section");
    String HOMEPAGE_RUNTIME_SERVER_SECTION = build(HOMEPAGE, "runtime-server-section");
    String HOST = "host";
    String HOST_REFRESH = build(HOST, REFRESH_SUFFIX);
    String HOST_PRUNE_ACTIONS = build(HOST, "prune-actions");
    String HOST_PRUNE_EXPIRED = build(HOST, "prune-expired");
    String HOST_PRUNE_DISCONNECTED = build(HOST, "prune-disconnected");
    String INTERFACE = "interface";
    String INTERFACE_ADD = build(INTERFACE, ADD_SUFFIX);
    String INTERFACE_REFRESH = build(INTERFACE, REFRESH_SUFFIX);
    String INVALIDATION_CACHE = "invalidation-cache";
    String JCA = "jca";
    String JCA_ARCHIVE_VALIDATION_FORM = build(JCA, "archive-validation", FORM_SUFFIX);
    String JCA_ARCHIVE_VALIDATION_TAB = build(JCA, "archive-validation", TAB_SUFFIX);
    String JCA_BEAN_VALIDATION_FORM = build(JCA, "bean-validation", FORM_SUFFIX);
    String JCA_BEAN_VALIDATION_TAB = build(JCA, "bean-validation", TAB_SUFFIX);
    String JCA_BOOTSTRAP_CONTEXT_ADD = build(JCA, "bootstrap-context", ADD_SUFFIX);
    String JCA_BOOTSTRAP_CONTEXT_ENTRY = build(JCA, "bootstrap-context", ENTRY_SUFFIX);
    String JCA_BOOTSTRAP_CONTEXT_FORM = build(JCA, "bootstrap-context", FORM_SUFFIX);
    String JCA_BOOTSTRAP_CONTEXT_TABLE = build(JCA, "bootstrap-context", TABLE_SUFFIX);
    String JCA_CCM_FORM = build(JCA, "ccm", FORM_SUFFIX);
    String JCA_CCM_TAB = build(JCA, "ccm", TAB_SUFFIX);
    String JCA_CONFIGURATION_ENTRY = build(JCA, "configuration", ENTRY_SUFFIX);
    String JCA_DISTRIBUTED_WORKMANAGER = build(JCA, "distributed-workmanager");
    String JCA_DISTRIBUTED_WORKMANAGER_ADD = build(JCA_DISTRIBUTED_WORKMANAGER, ADD_SUFFIX);
    String JCA_DISTRIBUTED_WORKMANAGER_ENTRY = build(JCA_DISTRIBUTED_WORKMANAGER, ENTRY_SUFFIX);
    String JCA_DISTRIBUTED_WORKMANAGER_FORM = build(JCA_DISTRIBUTED_WORKMANAGER, FORM_SUFFIX);
    String JCA_DISTRIBUTED_WORKMANAGER_PAGE = build(JCA_DISTRIBUTED_WORKMANAGER, PAGE_SUFFIX);
    String JCA_DISTRIBUTED_WORKMANAGER_TABLE = build(JCA_DISTRIBUTED_WORKMANAGER, TABLE_SUFFIX);
    String JCA_THREAD_POOL = "thread-pool";
    String JCA_THREAD_POOL_ADD = build(JCA_THREAD_POOL, ADD_SUFFIX);
    String JCA_THREAD_POOL_ATTRIBUTES_FORM = build(JCA_THREAD_POOL, "attributes", FORM_SUFFIX);
    String JCA_THREAD_POOL_ATTRIBUTES_TAB = build(JCA_THREAD_POOL, "attributes", TAB_SUFFIX);
    String JCA_THREAD_POOL_PAGE = build(JCA_THREAD_POOL, PAGE_SUFFIX);
    String JCA_THREAD_POOL_SIZING_FORM = build(JCA_THREAD_POOL, "sizing", FORM_SUFFIX);
    String JCA_THREAD_POOL_SIZING_TAB = build(JCA_THREAD_POOL, "sizing", TAB_SUFFIX);
    String JCA_THREAD_POOL_TABLE = build(JCA_THREAD_POOL, TABLE_SUFFIX);
    String JCA_TRACER_ENTRY = build(JCA, "tracer", ENTRY_SUFFIX);
    String JCA_TRACER_FORM = build(JCA, "tracer", FORM_SUFFIX);
    String JCA_WORKMANAGER = build(JCA, "workmanager");
    String JCA_WORKMANAGER_ADD = build(JCA_WORKMANAGER, ADD_SUFFIX);
    String JCA_WORKMANAGER_ENTRY = build(JCA_WORKMANAGER, ENTRY_SUFFIX);
    String JCA_WORKMANAGER_PAGE = build(JCA_WORKMANAGER, PAGE_SUFFIX);
    String JCA_WORKMANAGER_TABLE = build(JCA_WORKMANAGER, TABLE_SUFFIX);
    String JDBC_DRIVER = "jdbc-driver";
    String JDBC_DRIVER_ADD = build(JDBC_DRIVER, ADD_SUFFIX);
    String JDBC_DRIVER_ADD_FORM = build(JDBC_DRIVER, ADD_SUFFIX, FORM_SUFFIX);
    String JDBC_DRIVER_REFRESH = build(JDBC_DRIVER, REFRESH_SUFFIX);
    String JGROUPS = "jgroups";
    String JGROUPS_CHANNEL_CONFIG = build(JGROUPS, "channel");
    String JGROUPS_CHANNEL_ENTRY = build(JGROUPS, "channel", ENTRY_SUFFIX);
    String JGROUPS_CHANNEL_FORK = "fork";
    String JGROUPS_CHANNEL_FORK_ENTRY = build(JGROUPS_CHANNEL_ENTRY, JGROUPS_CHANNEL_FORK, ENTRY_SUFFIX);
    String JGROUPS_CHANNEL_FORK_PROTOCOL = "fork-protocol";
    String JGROUPS_ENTRY = build(JGROUPS, ENTRY_SUFFIX);
    String JGROUPS_FORM = build(JGROUPS, FORM_SUFFIX);
    String JGROUPS_PROTOCOL = "protocol";
    String JGROUPS_RELAY = "relay";
    String JGROUPS_REMOTE_SITE = "remote-site";
    String JGROUPS_STACK_COLUMN = build(JGROUPS, "stack-column");
    String JGROUPS_STACK_CONFIG = build(JGROUPS, "stack");
    String JGROUPS_STACK_ENTRY = build(JGROUPS, "stack", ENTRY_SUFFIX);
    String JGROUPS_TRANSPORT = "transport";
    String JGROUPS_TRANSPORT_THREADPOOL_DEFAULT_FORM = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-default",
            FORM_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_DEFAULT_TAB = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-default",
            TAB_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_INTERNAL_FORM = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-internal",
            FORM_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_INTERNAL_TAB = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-internal",
            TAB_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_OOB_FORM = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-oob", FORM_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_OOB_TAB = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-oob", TAB_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_TIMER_FORM = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-timer",
            FORM_SUFFIX);
    String JGROUPS_TRANSPORT_THREADPOOL_TIMER_TAB = build(JGROUPS, JGROUPS_TRANSPORT, "thread-pool-timer", TAB_SUFFIX);
    String JMS_BRIDGE = "jms-bridge";
    String JMS_BRIDGE_ADD = build(JMS_BRIDGE, ADD_SUFFIX);
    String JMS_BRIDGE_FORM = build(JMS_BRIDGE, FORM_SUFFIX);
    String JMS_BRIDGE_REFRESH = build(JMS_BRIDGE, REFRESH_SUFFIX);
    String JMS_BRIDGE_TAB = build(JMS_BRIDGE, TAB_SUFFIX);
    String JMS_MESSAGE = "jms-message";
    String JMS_MESSAGE_CHANGE_PRIORITY = Ids.build(JMS_MESSAGE, "change-priority");
    String JMS_MESSAGE_CHANGE_PRIORITY_FORM = Ids.build(JMS_MESSAGE, "change-priority", FORM_SUFFIX);
    String JMS_MESSAGE_EXPIRE = Ids.build(JMS_MESSAGE, "expire");
    String JMS_MESSAGE_MOVE = Ids.build(JMS_MESSAGE, "move");
    String JMS_MESSAGE_MOVE_FORM = Ids.build(JMS_MESSAGE, "move", FORM_SUFFIX);
    String JMS_MESSAGE_REMOVE = Ids.build(JMS_MESSAGE, "remove");
    String JMS_MESSAGE_SEND_TO_DEAD_LETTER = Ids.build(JMS_MESSAGE, "send-to-dead-letter");
    String JMS_MESSAGE_LIST = "jms-message-list";
    String JMS_MESSAGE_LIST_CHANGE_PRIORITY = Ids.build(JMS_MESSAGE_LIST, "change-priority");
    String JMS_MESSAGE_LIST_CLEAR_SELECTION = Ids.build(JMS_MESSAGE_LIST, "clear-selection");
    String JMS_MESSAGE_LIST_EXPIRE = Ids.build(JMS_MESSAGE_LIST, "expire");
    String JMS_MESSAGE_LIST_MOVE = Ids.build(JMS_MESSAGE_LIST, "move");
    String JMS_MESSAGE_LIST_REFRESH = Ids.build(JMS_MESSAGE_LIST, "refresh");
    String JMS_MESSAGE_LIST_REMOVE = Ids.build(JMS_MESSAGE_LIST, "remove");
    String JMS_MESSAGE_LIST_SELECT_ALL = Ids.build(JMS_MESSAGE_LIST, "select-all");
    String JMS_MESSAGE_LIST_SEND_TO_DEAD_LETTER = Ids.build(JMS_MESSAGE_LIST, "send-to-dead-letter");
    String JMX = "jmx";
    String JMX_AUDIT_LOG_ENTRY = build(JMX, "audit-log", ENTRY_SUFFIX);
    String JMX_AUDIT_LOG_FORM = build(JMX, "audit-log", FORM_SUFFIX);
    String JMX_CONFIGURATION_ENTRY = build(JMX, "configuration", ENTRY_SUFFIX);
    String JMX_CONFIGURATION_FORM = build(JMX, "configuration", FORM_SUFFIX);
    String JMX_REMOTING_CONNECTOR_ENTRY = build(JMX, "remoting-connector", ENTRY_SUFFIX);
    String JMX_REMOTING_CONNECTOR_FORM = build(JMX, "remoting-connector", FORM_SUFFIX);
    String JNDI = "jndi";
    String JNDI_DETAILS = build(JNDI, "details");
    String JNDI_SEARCH = build(JNDI, "search");
    String JNDI_TREE = build(JNDI, "tree");
    String JNDI_TREE_APPLICATIONS_ROOT = build(JNDI_TREE, "applications-root");
    String JNDI_TREE_JAVA_CONTEXTS_ROOT = build(JNDI_TREE, "java-contexts-root");
    String JOB = "job";
    String JOP_EXECUTION_REFRESH = build(JOB, "execution", "refresh");
    String JOP_EXECUTION_RESTART = build(JOB, "execution", "restart");
    String JOP_EXECUTION_STOP = build(JOB, "execution", "stop");
    String JOB_LIST = build(JOB, "list");
    String JOB_REFRESH = build(JOB, REFRESH_SUFFIX);
    String JPA_RUNTIME = "jpa-runtime";
    String JPA_RUNTIME_COLLECTION_ENTRY = build(JPA_RUNTIME, "collection", ENTRY_SUFFIX);
    String JPA_RUNTIME_ENTITY_CACHE_ENTRY = build(JPA_RUNTIME, "entity-cache", ENTRY_SUFFIX);
    String JPA_RUNTIME_ENTITY_ENTRY = build(JPA_RUNTIME, "entity", ENTRY_SUFFIX);
    String JPA_RUNTIME_MAIN_ATTRIBUTES_ENTRY = build(JPA_RUNTIME, "main", "attributes", ENTRY_SUFFIX);
    String JPA_RUNTIME_QUERY_CACHE_ENTRY = build(JPA_RUNTIME, "query-cache", ENTRY_SUFFIX);
    String LOCAL_CACHE = "local-cache";
    String LOG_FILE = "lf";
    String LOG_FILE_EDITOR = build(LOG_FILE, "editor");
    String LOG_FILE_REFRESH = build(LOG_FILE, REFRESH_SUFFIX);
    String LOG_FILE_SEARCH = build(LOG_FILE, "search");
    String LOGGING = "logging";
    String LOGGING_CONFIGURATION = "logging-configuration";
    String LOGGING_PROFILE = "logging-profile";
    String LOGGING_PROFILE_ADD = build(LOGGING_PROFILE, ADD_SUFFIX);
    String MACRO = "macro";
    String MACRO_EDITOR = build(MACRO, "editor");
    String MACRO_LIST = build(MACRO, "list");
    String MACRO_OPTIONS = build(MACRO, "options");
    String MACRO_STORAGE = build(STORAGE_PREFIX, MACRO);
    String MAIL_SERVER = "mail-server";
    String MAIL_SERVER_DIALOG = build(MAIL_SERVER, ADD_SUFFIX, FORM_SUFFIX);
    String MAIL_SERVER_ENTRY = build(MAIL_SERVER, ENTRY_SUFFIX);
    String MAIL_SERVER_FORM = build(MAIL_SERVER, FORM_SUFFIX);
    String MAIL_SERVER_TABLE = build(MAIL_SERVER, TABLE_SUFFIX);
    String MAIL_SESSION = "mail-session";
    String MAIL_SESSION_ADD = build(MAIL_SESSION, ADD_SUFFIX);
    String MAIL_SESSION_DIALOG = build(MAIL_SESSION, FORM_SUFFIX);
    String MAIL_SESSION_ENTRY = build(MAIL_SESSION, ENTRY_SUFFIX);
    String MAIL_SESSION_FORM = build(MAIL_SESSION, FORM_SUFFIX);
    String MAIL_SESSION_REFRESH = build(MAIL_SESSION, REFRESH_SUFFIX);
    String MANAGEMENT = "management";
    String MEMBERSHIP = "membership";
    String MEMBERSHIP_EXCLUDE = build(MEMBERSHIP, "exclude");
    String MEMBERSHIP_INCLUDE = build(MEMBERSHIP, "include");
    String MESSAGING = "messaging";
    String MESSAGING_ACCEPTOR = build(MESSAGING, "acceptor");
    String MESSAGING_ADDRESS_SETTING = build("address-setting");
    String MESSAGING_BRIDGE = build(MESSAGING, "bridge");
    String MESSAGING_BROADCAST_GROUP = build(MESSAGING, "broadcast-group");
    String MESSAGING_CATEGORY = "messaging-category";
    String MESSAGING_CLUSTER_CONNECTION = build(MESSAGING, "cluster-connection");
    String MESSAGING_CONNECTION_FACTORY = build(MESSAGING, "connection-factory");
    String MESSAGING_CONNECTOR = build(MESSAGING, "connector");
    String MESSAGING_CONNECTOR_SERVICE = build(MESSAGING, "connector-service");
    String MESSAGING_CORE_QUEUE = build(MESSAGING, "core-queue");
    String MESSAGING_DISCOVERY_GROUP = build(MESSAGING, "discovery-group");
    String MESSAGING_DIVERT = build(MESSAGING, "divert");
    String MESSAGING_GLOBAL_SETTINGS = build(MESSAGING, "global-settings");
    String MESSAGING_GROUPING_HANDLER = build(MESSAGING, "grouping-handler");
    String MESSAGING_HA = build(MESSAGING, "ha");
    String MESSAGING_HA_CHOOSE_STRATEGY = build(MESSAGING_HA, "choose-strategy");
    String MESSAGING_HA_REPLICATION = build(MESSAGING_HA, "replication");
    String MESSAGING_HA_REPLICATION_COLOCATED = build(MESSAGING_HA_REPLICATION, "colocated");
    String MESSAGING_HA_REPLICATION_COLOCATED_MASTER = build(MESSAGING_HA_REPLICATION_COLOCATED, "master");
    String MESSAGING_HA_REPLICATION_COLOCATED_SLAVE = build(MESSAGING_HA_REPLICATION_COLOCATED, "slave");
    String MESSAGING_HA_REPLICATION_LIVE_ONLY = build(MESSAGING_HA_REPLICATION, "live-only");
    String MESSAGING_HA_REPLICATION_MASTER = build(MESSAGING_HA_REPLICATION, "master");
    String MESSAGING_HA_REPLICATION_SLAVE = build(MESSAGING_HA_REPLICATION, "replication-slave");
    String MESSAGING_HA_SHARED_STORE = build(MESSAGING_HA, "shared-store");
    String MESSAGING_HA_SHARED_STORE_COLOCATED = build(MESSAGING_HA_SHARED_STORE, "colocated");
    String MESSAGING_HA_SHARED_STORE_COLOCATED_MASTER = build(MESSAGING_HA_SHARED_STORE_COLOCATED, "master");
    String MESSAGING_HA_SHARED_STORE_COLOCATED_SLAVE = build(MESSAGING_HA_SHARED_STORE_COLOCATED, "slave");
    String MESSAGING_HA_SHARED_STORE_MASTER = build(MESSAGING_HA_SHARED_STORE, "master");
    String MESSAGING_HA_SHARED_STORE_SLAVE = build(MESSAGING_HA_SHARED_STORE, "slave");
    String MESSAGING_HTTP_ACCEPTOR = build(MESSAGING, "http-acceptor");
    String MESSAGING_HTTP_CONNECTOR = build(MESSAGING, "http-connector");
    String MESSAGING_IN_VM_ACCEPTOR = build(MESSAGING, "in-vm-acceptor");
    String MESSAGING_IN_VM_CONNECTOR = build(MESSAGING, "in-vm-connector");
    String MESSAGING_JMS_QUEUE = build(MESSAGING, "jms-queue");
    String MESSAGING_JMS_TOPIC = build(MESSAGING, "jms-topic");
    String MESSAGING_POOLED_CONNECTION_FACTORY = build(MESSAGING, "pooled-connection-factory");
    String MESSAGING_REMOTE_ACCEPTOR = build(MESSAGING, "remote-acceptor");
    String MESSAGING_REMOTE_CONNECTOR = build(MESSAGING, "remote-connector");
    String MESSAGING_SECURITY_SETTING_ROLE = build(MESSAGING, "security-setting-role");
    String MESSAGING_SECURITY_SETTING_ROLE_ADD = build(MESSAGING_SECURITY_SETTING_ROLE, ADD_SUFFIX);
    String MESSAGING_SECURITY_SETTING_ROLE_ENTRY = build(MESSAGING_SECURITY_SETTING_ROLE, ENTRY_SUFFIX);
    String MESSAGING_SECURITY_SETTING_ROLE_FORM = build(MESSAGING_SECURITY_SETTING_ROLE, FORM_SUFFIX);
    String MESSAGING_SECURITY_SETTING_ROLE_TABLE = build(MESSAGING_SECURITY_SETTING_ROLE, TABLE_SUFFIX);
    String MESSAGING_SERVER = "msg-server";
    String MESSAGING_SERVER_ADD = build(MESSAGING_SERVER, ADD_SUFFIX);
    String MESSAGING_SERVER_CLUSTERING = build(MESSAGING_SERVER, "clustering");
    String MESSAGING_SERVER_CONFIGURATION = "msg-server-c";
    String MESSAGING_SERVER_CONFIGURATION_REFRESH = build(MESSAGING_SERVER_CONFIGURATION, REFRESH_SUFFIX);
    String MESSAGING_SERVER_CONNECTION = build(MESSAGING_SERVER, "connection");
    String MESSAGING_SERVER_DESTINATION = "msg-server-destination";
    String MESSAGING_SERVER_DESTINATION_REFRESH = build(MESSAGING_SERVER_DESTINATION, REFRESH_SUFFIX);
    String MESSAGING_SERVER_HA_POLICY = build(MESSAGING_SERVER, "ha-policy");
    String MESSAGING_SERVER_RUNTIME = "msg-server-r";
    String MESSAGING_SERVER_RUNTIME_REFRESH = build(MESSAGING_SERVER_RUNTIME, REFRESH_SUFFIX);
    String MESSAGING_SERVER_SETTINGS = "msg-server-settings";
    String MESSAGING_SERVER_JOURNAL_DIRECTORY = build(MESSAGING_SERVER, "journal-directory");
    String MESSAGING_SERVER_JOURNAL_DIRECTORY_FORM = build(MESSAGING_SERVER, "journal-directory", FORM_SUFFIX);
    String MESSAGING_SERVER_PAGING_DIRECTORY = build(MESSAGING_SERVER, "paging-directory");
    String MESSAGING_SERVER_PAGING_DIRECTORY_FORM = build(MESSAGING_SERVER, "paging-directory", FORM_SUFFIX);
    String MESSAGING_SERVER_BINDING_DIRECTORY = build(MESSAGING_SERVER, "bindings-directory");
    String MESSAGING_SERVER_BINDING_DIRECTORY_FORM = build(MESSAGING_SERVER, "bindings-directory", FORM_SUFFIX);
    String MESSAGING_SERVER_LARGE_MESSAGES_DIRECTORY = build(MESSAGING_SERVER, "large-messages-directory");
    String MESSAGING_SERVER_LARGE_MESSAGES_DIRECTORY_FORM = build(MESSAGING_SERVER, "large-messages-directory",
            FORM_SUFFIX);
    String MODEL_BROWSER = "model-browser";
    String MODEL_BROWSER_CREATE_SINGLETON_FORM = build(MODEL_BROWSER, "create-singleton", FORM_SUFFIX);
    String MODEL_BROWSER_ROOT = build(MODEL_BROWSER, "root");
    String PATCH_ADD = build("patch", ADD_SUFFIX);
    String PATCH_UPLOAD_NAMES_FORM = build("patch", "names", FORM_SUFFIX);
    String PATCHING = "patching";
    String PATCHES_AGEOUT = build(PATCHING, "ageout-history");
    String PATCHES_REFRESH = build(PATCHING, REFRESH_SUFFIX);
    String PATCHING_DOMAIN = "patching-domain";
    String PREVIEW_ID = build(FINDER, "preview");
    String PROFILE = "profile";
    String PROFILE_ADD = build(PROFILE, ADD_SUFFIX);
    String PROFILE_CLONE = build(PROFILE, "clone");
    String PROFILE_REFRESH = build(PROFILE, REFRESH_SUFFIX);
    String REMOTING = "remoting";
    String REMOTING_CONNECTOR_FORM = build(REMOTING, "connector", FORM_SUFFIX);
    String REMOTING_CONNECTOR_SECURITY_FORM = build(REMOTING, "connector-security", FORM_SUFFIX);
    String REMOTING_CONNECTOR_SECURITY_POLICY_FORM = build(REMOTING, "connector-security-policy", FORM_SUFFIX);
    String REMOTING_CONNECTOR_SECURITY_POLICY_TAB = build(REMOTING, "connector-security-policy", TAB_SUFFIX);
    String REMOTING_CONNECTOR_SECURITY_TAB = build(REMOTING, "connector-security", TAB_SUFFIX);
    String REMOTING_CONNECTOR_TAB = build(REMOTING, "connector", TAB_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_FORM = build(REMOTING, "http-connector", FORM_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_SECURITY_FORM = build(REMOTING, "http-connector-security", FORM_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_SECURITY_POLICY_FORM = build(REMOTING, "http-connector-security-policy",
            FORM_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_SECURITY_POLICY_TAB = build(REMOTING, "http-connector-security-policy", TAB_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_SECURITY_TAB = build(REMOTING, "http-connector-security", TAB_SUFFIX);
    String REMOTING_HTTP_CONNECTOR_TAB = build(REMOTING, "http-connector", TAB_SUFFIX);
    String REPLICATED_CACHE = "replicated-cache";
    String RESOLVE_EXPRESSION = "resolve-expression";
    String RESOLVE_EXPRESSION_FORM = build(RESOLVE_EXPRESSION, FORM_SUFFIX);
    String RESOURCE_ADAPTER = "resource-adapter";
    String RESOURCE_ADAPTER_ADD = build(RESOURCE_ADAPTER, ADD_SUFFIX);
    String RESOURCE_ADAPTER_ADMIN_OBJECT_ADD = build(RESOURCE_ADAPTER, "admin-object", ADD_SUFFIX);
    String RESOURCE_ADAPTER_CONNECTION_DEFINITION_ADD = build(RESOURCE_ADAPTER, "connection-definition", ADD_SUFFIX);
    String RESOURCE_ADAPTER_FORM = build(RESOURCE_ADAPTER, FORM_SUFFIX);
    String REST_RESOURCE = "rest-rsc";
    String REST_RESOURCE_REFRESH = build(REST_RESOURCE, REFRESH_SUFFIX);
    String REST_RESOURCE_PATH_PARAM_FORM = build(REST_RESOURCE, "path-param", FORM_SUFFIX);
    String ROLE = "role";
    String ROLE_ADD = build(ROLE, ADD_SUFFIX);
    String ROLE_HOST_SCOPED_ADD = build(ROLE, HOST, ADD_SUFFIX);
    String ROLE_HOST_SCOPED_FORM = build(ROLE, HOST, FORM_SUFFIX);
    String ROLE_MAPPING_FORM = build("role-mapping", FORM_SUFFIX);
    String ROLE_REFRESH = build(ROLE, REFRESH_SUFFIX);
    String ROLE_SERVER_GROUP_SCOPED_ADD = build(ROLE, "server-group", ADD_SUFFIX);
    String ROLE_SERVER_GROUP_SCOPED_FORM = build(ROLE, "server-group", FORM_SUFFIX);
    String ROOT_CONTAINER = "hal-root-container";
    String RUNTIME_SUBSYSTEM = "rss";
    String SECURITY_DOMAIN = "security-domain";
    String SECURITY_DOMAIN_ACL_MODULE_ADD = build(SECURITY_DOMAIN, "acl", ADD_SUFFIX);
    String SECURITY_DOMAIN_ADD = build(SECURITY_DOMAIN, ADD_SUFFIX);
    String SECURITY_DOMAIN_AUDIT_ADD = build(SECURITY_DOMAIN, "provider", ADD_SUFFIX);
    String SECURITY_DOMAIN_AUTHENTICATION_ADD = build(SECURITY_DOMAIN, "authentication", ADD_SUFFIX);
    String SECURITY_DOMAIN_AUTHORIZATION_ADD = build(SECURITY_DOMAIN, "authorization", ADD_SUFFIX);
    String SECURITY_DOMAIN_MAPPING_ADD = build(SECURITY_DOMAIN, "mapping", ADD_SUFFIX);
    String SECURITY_DOMAIN_TRUST_MODULE_ADD = build(SECURITY_DOMAIN, "identity-trust", ADD_SUFFIX);
    String SERVER = "server";
    String SERVER_ADD = build(SERVER, ADD_SUFFIX);
    String SERVER_GROUP = "server-group";
    String SERVER_GROUP_ADD = build(SERVER_GROUP, ADD_SUFFIX);
    String SERVER_GROUP_DEPLOYMENT = "server-group-deployment";
    String SERVER_GROUP_DEPLOYMENT_ADD = build(SERVER_GROUP_DEPLOYMENT, ADD_SUFFIX);
    String SERVER_GROUP_DEPLOYMENT_ADD_ACTIONS = build(SERVER_GROUP_DEPLOYMENT, "add-actions");
    String SERVER_GROUP_DEPLOYMENT_ENABLE = build(SERVER_GROUP_DEPLOYMENT, "enable");
    String SERVER_GROUP_DEPLOYMENT_REFRESH = build(SERVER_GROUP_DEPLOYMENT, REFRESH_SUFFIX);
    String SERVER_GROUP_DEPLOYMENT_TABLE = build(SERVER_GROUP_DEPLOYMENT, TABLE_SUFFIX);
    String SERVER_GROUP_DEPLOYMENT_UNMANAGED_ADD = build(SERVER_GROUP_DEPLOYMENT, "unmanaged", ADD_SUFFIX);
    String SERVER_GROUP_DEPLOYMENT_UPLOAD = build(SERVER_GROUP_DEPLOYMENT, "upload");
    String SERVER_GROUP_REFRESH = build(SERVER_GROUP, REFRESH_SUFFIX);
    String SERVER_REFRESH = build(SERVER, REFRESH_SUFFIX);
    String SERVER_RUNTIME = build(SERVER, "runtime");
    String SERVER_RUNTIME_ITEM = build(SERVER_RUNTIME, "item");
    String SERVER_RUNTIME_BOOTSTRAP_FORM = build(SERVER_RUNTIME, "bootstrap", FORM_SUFFIX);
    String SERVER_RUNTIME_BOOTSTRAP_TAB = build(SERVER_RUNTIME, "bootstrap", TAB_SUFFIX);
    String SERVER_RUNTIME_JVM_ATTRIBUTES_FORM = build(SERVER_RUNTIME, "jvm-attributes", FORM_SUFFIX);
    String SERVER_RUNTIME_JVM_ATTRIBUTES_TAB = build(SERVER_RUNTIME, "jvm-attributes", TAB_SUFFIX);
    String SERVER_RUNTIME_PROPERTIES_TAB = build(SERVER_RUNTIME, "properties", TAB_SUFFIX);
    String SERVER_RUNTIME_PROPERTIES_TABLE = build(SERVER_RUNTIME, "properties", TABLE_SUFFIX);
    String SERVER_RUNTIME_STATUS = build(SERVER_RUNTIME, "status");
    String SERVER_STATUS_BOOTSTRAP_ENTRY = build(SERVER_RUNTIME, "bootstrap", ENTRY_SUFFIX);
    String SERVER_STATUS_MAIN_ATTRIBUTES_ENTRY = build(SERVER_RUNTIME, "main-attributes", ENTRY_SUFFIX);
    String SERVER_STATUS_SYSTEM_PROPERTIES_ENTRY = build(SERVER_RUNTIME, "system-properties", ENTRY_SUFFIX);
    String SERVER_URL = "server-url";
    String SERVER_URL_FORM = build(SERVER_URL, FORM_SUFFIX);
    String SERVER_URL_STORAGE = build(STORAGE_PREFIX, SERVER_URL);
    String SETTINGS_FORM = "settings-form";
    String SOCKET_BINDING_GROUP = "socket-binding-group";
    String SOCKET_BINDING_GROUP_ADD = build(SOCKET_BINDING_GROUP, ADD_SUFFIX);
    String SOCKET_BINDING_GROUP_INBOUND = build(SOCKET_BINDING_GROUP, "inbound");
    String SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING = build(SOCKET_BINDING_GROUP_INBOUND, "client-mapping");
    String SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING_ADD = build(SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING,
            ADD_SUFFIX);
    String SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING_FORM = build(SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING,
            FORM_SUFFIX);
    String SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING_PAGE = build(SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING,
            PAGE_SUFFIX);
    String SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING_TABLE = build(SOCKET_BINDING_GROUP_INBOUND_CLIENT_MAPPING,
            TABLE_SUFFIX);
    String SOCKET_BINDING_GROUP_OUTBOUND_LOCAL = build(SOCKET_BINDING_GROUP, "outbound-local");
    String SOCKET_BINDING_GROUP_OUTBOUND_REMOTE = build(SOCKET_BINDING_GROUP, "outbound-remote");
    String SOCKET_BINDING_GROUP_REFRESH = build(SOCKET_BINDING_GROUP, REFRESH_SUFFIX);
    String STANDALONE_HOST = "standalone-host";
    String STANDALONE_SERVER = "standalone-server";
    String SYSTEM_PROPERTY_ADD = "system-property-add";
    String TASKS = "tasks";
    String TASKS_ACTIVE = build(TASKS, "active");
    String TASKS_COMPLETED = build(TASKS, "completed");
    String TASKS_QUEUE = build(TASKS, "queue");
    String TASKS_REJECTED = build(TASKS, "rejected");
    String TLC_ACCESS_CONTROL = "tlc-access-control";
    String TLC_CONFIGURATION = "tlc-configuration";
    String TLC_DEPLOYMENTS = "tlc-deployments";
    String TLC_HOMEPAGE = "tlc-homepage";
    String TLC_MANAGEMENT = "tlc-management";
    String TLC_PATCHING = "tlc-patching";
    String TLC_RUNTIME = "tlc-runtime";
    String TOOLBAR = "toolbar";
    String TOOLBAR_FILTER = build(TOOLBAR, "filter");
    String TOOLBAR_ACTION_DROPDOWN = build(TOOLBAR, "action-dropdown");
    String TRANSACTION = "transaction";
    String TRANSACTION_PARTICIPANTS_PAGE = build(TRANSACTION, PARTICIPANTS, PAGE_SUFFIX);
    String UNDERTOW_APP_SECURITY_DOMAIN = "undertow-application-security-domain";
    String UNDERTOW_APP_SECURITY_DOMAIN_ADD = build(UNDERTOW_APP_SECURITY_DOMAIN, ADD_SUFFIX);
    String UNDERTOW_APP_SECURITY_DOMAIN_FORM = build(UNDERTOW_APP_SECURITY_DOMAIN, FORM_SUFFIX);
    String UNDERTOW_APP_SECURITY_DOMAIN_REFRESH = build(UNDERTOW_APP_SECURITY_DOMAIN, REFRESH_SUFFIX);
    String UNDERTOW_APP_SECURITY_DOMAIN_TAB = build(UNDERTOW_APP_SECURITY_DOMAIN, TABLE_SUFFIX);
    String UNDERTOW_GLOBAL_SETTINGS = "undertow-global-settings";
    String UNDERTOW_LISTENER_REFRESH = build("undertow-listener", REFRESH_SUFFIX);
    String UNDERTOW_HOST = "undertow-host";
    String UNDERTOW_HOST_ACCESS_LOG = build(UNDERTOW_HOST, "access-log");
    String UNDERTOW_HOST_ACTION_COLUMN = build(UNDERTOW_HOST, "action-column");
    String UNDERTOW_HOST_ADD = build(UNDERTOW_HOST, ADD_SUFFIX);
    String UNDERTOW_HOST_ATTRIBUTES_FORM = build(UNDERTOW_HOST, FORM_SUFFIX);
    String UNDERTOW_HOST_ATTRIBUTES_TAB = build(UNDERTOW_HOST, TAB_SUFFIX);
    String UNDERTOW_HOST_ENTRY = build(UNDERTOW_HOST, ENTRY_SUFFIX);
    String UNDERTOW_HOST_FILTER_REF_ADD = build(UNDERTOW_HOST, "filter-ref", ADD_SUFFIX);
    String UNDERTOW_HOST_FILTER_REF_FORM = build(UNDERTOW_HOST, "filter-ref", FORM_SUFFIX);
    String UNDERTOW_HOST_FILTER_REF_PAGE = build(UNDERTOW_HOST, "filter-ref", PAGE_SUFFIX);
    String UNDERTOW_HOST_FILTER_REF_TABLE = build(UNDERTOW_HOST, "filter-ref", TABLE_SUFFIX);
    String UNDERTOW_HOST_HTTP_INVOKER = build(UNDERTOW_HOST, "http-invoker");
    String UNDERTOW_HOST_LOCATION_ADD = build(UNDERTOW_HOST, "location", ADD_SUFFIX);
    String UNDERTOW_HOST_LOCATION_FILTER_REF_ADD = build(UNDERTOW_HOST, "location", "filter-ref", ADD_SUFFIX);
    String UNDERTOW_HOST_LOCATION_FILTER_REF_FORM = build(UNDERTOW_HOST, "location", "filter-ref", FORM_SUFFIX);
    String UNDERTOW_HOST_LOCATION_FILTER_REF_PAGE = build(UNDERTOW_HOST, "location", "filter-ref", PAGE_SUFFIX);
    String UNDERTOW_HOST_LOCATION_FILTER_REF_TABLE = build(UNDERTOW_HOST, "location", "filter-ref", TABLE_SUFFIX);
    String UNDERTOW_HOST_LOCATION_FORM = build(UNDERTOW_HOST, "location", FORM_SUFFIX);
    String UNDERTOW_HOST_LOCATION_PAGE = build(UNDERTOW_HOST, "location", PAGE_SUFFIX);
    String UNDERTOW_HOST_LOCATION_TABLE = build(UNDERTOW_HOST, "location", TABLE_SUFFIX);
    String UNDERTOW_HOST_MAIN_PAGE = build(UNDERTOW_HOST, "main", PAGE_SUFFIX);
    String UNDERTOW_HOST_SINGLE_SIGN_ON = build(UNDERTOW_HOST, "single-sign-on");
    String UNDERTOW_HOST_TABLE = build(UNDERTOW_HOST, TABLE_SUFFIX);
    String UNDERTOW_MODCLUSTER_BALANCER_NODE_CONTEXT_REFRESH = build("modcluster", "balancer", "node", "context",
            REFRESH_SUFFIX);
    String UNDERTOW_MODCLUSTER_BALANCER_NODE_REFRESH = build("modcluster", "balancer", "node", REFRESH_SUFFIX);
    String UNDERTOW_MODCLUSTER_BALANCER_REFRESH = build("modcluster", "balancer", REFRESH_SUFFIX);
    String UNDERTOW_MODCLUSTER_REFRESH = build("modcluster", REFRESH_SUFFIX);
    String UNDERTOW_RESPONSE_HEADER_ADD = build("undertow-response-header", ADD_SUFFIX);
    String UNDERTOW_RUNTIME = "undertow-runtime";
    String UNDERTOW_RUNTIME_APP_SEC_DOMAIN = "undertow-runtime-app-sec-domain";
    String UNDERTOW_RUNTIME_APP_SEC_DOMAIN_REFRESH = "undertow-runtime-app-sec-domain-refresh";
    String UNDERTOW_RUNTIME_DEPLOYMENT = "undertow-runtime-deployment";
    String UNDERTOW_RUNTIME_REFRESH = build("undertow-runtime-deployment", REFRESH_SUFFIX);
    String UNDERTOW_RUNTIME_LISTENER = "undertow-runtime-listener";
    String UNDERTOW_RUNTIME_MODCLUSTER = "undertow-runtime-modcluster";
    String UNDERTOW_RUNTIME_MODCLUSTER_BALANCER = "undertow-runtime-modcluster-balancer";
    String UNDERTOW_RUNTIME_MODCLUSTER_BALANCER_NODE = "undertow-runtime-modcluster-balancer-node";
    String UNDERTOW_RUNTIME_MODCLUSTER_BALANCER_NODE_CONTEXT = "undertow-runtime-modcluster-balancer-node-context";
    String UNDERTOW_RUNTIME_SERVER = "undertow-runtime-server";
    String UNDERTOW_SERVER = "undertow-server";
    String UNDERTOW_SERVER_ADD = build(UNDERTOW_SERVER, ADD_SUFFIX);
    String UNDERTOW_SERVER_AJP_LISTENER = build(UNDERTOW_SERVER, "ajp-listener");
    String UNDERTOW_SERVER_CONFIGURATION_ENTRY = build(UNDERTOW_SERVER, "configuration", ENTRY_SUFFIX);
    String UNDERTOW_SERVER_CONFIGURATION_FORM = build(UNDERTOW_SERVER, "configuration", FORM_SUFFIX);
    String UNDERTOW_SERVER_HTTP_LISTENER = build(UNDERTOW_SERVER, "http-listener");
    String UNDERTOW_SERVER_HTTPS_LISTENER = build(UNDERTOW_SERVER, "https-listener");
    String UNDERTOW_SERVER_LISTENER_ENTRY = build(UNDERTOW_SERVER, "listener", ENTRY_SUFFIX);
    String UNDERTOW_SERVER_REFRESH = build(UNDERTOW_SERVER, REFRESH_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER = "undertow-servlet-container";
    String UNDERTOW_SERVLET_CONTAINER_ADD = build(UNDERTOW_SERVLET_CONTAINER, ADD_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_CONFIGURATION_ENTRY = build(UNDERTOW_SERVLET_CONTAINER, "configuration",
            ENTRY_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_CONFIGURATION_FORM = build(UNDERTOW_SERVLET_CONTAINER, "configuration",
            FORM_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_CONFIGURATION_TAB = build(UNDERTOW_SERVLET_CONTAINER, "configuration",
            TAB_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_COOKIE = build(UNDERTOW_SERVLET_CONTAINER, "cookie");
    String UNDERTOW_SERVLET_CONTAINER_CRAWLER = build(UNDERTOW_SERVLET_CONTAINER, "crawler");
    String UNDERTOW_SERVLET_CONTAINER_JSP = build(UNDERTOW_SERVLET_CONTAINER, "jsp");
    String UNDERTOW_SERVLET_CONTAINER_MIME_MAPPING_FORM = build(UNDERTOW_SERVLET_CONTAINER, "mime-mapping",
            FORM_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_MIME_MAPPING_TAB = build(UNDERTOW_SERVLET_CONTAINER, "mime-mapping", TAB_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_REFRESH = build(UNDERTOW_SERVLET_CONTAINER, REFRESH_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_SESSION = build(UNDERTOW_SERVLET_CONTAINER, "session");
    String UNDERTOW_SERVLET_CONTAINER_WEBSOCKET = build(UNDERTOW_SERVLET_CONTAINER, "websocket");
    String UNDERTOW_SERVLET_CONTAINER_WELCOME_FILE_FORM = build(UNDERTOW_SERVLET_CONTAINER, "welcome-file",
            FORM_SUFFIX);
    String UNDERTOW_SERVLET_CONTAINER_WELCOME_FILE_TAB = build(UNDERTOW_SERVLET_CONTAINER, "welcome-file", TAB_SUFFIX);
    String UNDERTOW_SETTINGS = "undertow-settings";
    String UNDERTOW_SINGLE_SIGN_ON = "undertow-single-sign-on";
    String UNDERTOW_SINGLE_SIGN_ON_ADD = build(UNDERTOW_SINGLE_SIGN_ON, ADD_SUFFIX);
    String UNDERTOW_SINGLE_SIGN_ON_FORM = build(UNDERTOW_SINGLE_SIGN_ON, FORM_SUFFIX);
    String UNDERTOW_SINGLE_SIGN_ON_TAB = build(UNDERTOW_SINGLE_SIGN_ON, TABLE_SUFFIX);
    String UNMANAGED = "unmanaged";
    String UNMANAGED_FORM = build(UNMANAGED, FORM_SUFFIX);
    String UPLOAD = "upload";
    String UPLOAD_FILE_INPUT = build(UPLOAD, "file-input");
    String UPLOAD_NAMES_FORM = build(UPLOAD, "names", FORM_SUFFIX);
    String USER = "user";
    String WEBSERVICES = "webservices";
    String WEBSERVICES_CLIENT_CONFIG = build(WEBSERVICES, "client-config");
    String WEBSERVICES_CLIENT_CONFIG_ENTRY = build(WEBSERVICES, "client-config", ENTRY_SUFFIX);
    String WEBSERVICES_ENDPOINT_CONFIG = build(WEBSERVICES, "endpoint-config");
    String WEBSERVICES_ENDPOINT_CONFIG_ENTRY = build(WEBSERVICES, "endpoint-config", ENTRY_SUFFIX);
    String WEBSERVICES_ENTRY = build(WEBSERVICES, ENTRY_SUFFIX);
    String WEBSERVICES_FORM = build(WEBSERVICES, FORM_SUFFIX);
    String WEBSERVICES_HANDLER_ADD = build(WEBSERVICES, "handler", ADD_SUFFIX);
    String WEBSERVICES_HANDLER_CHAIN_ADD = build(WEBSERVICES, "handler-chain", ADD_SUFFIX);
    String WEBSERVICES_HANDLER_CHAIN_COLUMN = build(WEBSERVICES, "handler-chain-column");
    String WORKER = "worker";
    String XA_DATA_SOURCE = "xa-data-source";
    String XA_DATA_SOURCE_ADD = build(XA_DATA_SOURCE, ADD_SUFFIX);
    String XA_DATA_SOURCE_FORM = build(XA_DATA_SOURCE, FORM_SUFFIX);
    String XA_DATA_SOURCE_RUNTIME = "xa-data-source-runtime";
    String XA_DATA_SOURCE_RUNTIME_JDBC_FORM = build(XA_DATA_SOURCE_RUNTIME, "jdbc", FORM_SUFFIX);
    String XA_DATA_SOURCE_RUNTIME_JDBC_TAB = build(XA_DATA_SOURCE_RUNTIME, "jdbc", TAB_SUFFIX);
    String XA_DATA_SOURCE_RUNTIME_POOL_FORM = build(XA_DATA_SOURCE_RUNTIME, "pool", FORM_SUFFIX);
    String XA_DATA_SOURCE_RUNTIME_POOL_TAB = build(XA_DATA_SOURCE_RUNTIME, "pool", TAB_SUFFIX);


    // ------------------------------------------------------ resource ids (a-z)

    static String cacheContainer(String name) {
        return Ids.build(CACHE_CONTAINER, name);
    }

    static String content(String name) {
        return name;
    }

    static String dataSourceConfiguration(String name, boolean xa) {
        return build(xa ? "xa" : "non-xa", DATA_SOURCE_CONFIGURATION, name);
    }

    static String dataSourceRuntime(String name, boolean xa) {
        return build(xa ? "xa" : "non-xa", DATA_SOURCE_RUNTIME, name);
    }

    static String deployment(String name) {
        return name;
    }

    static String destination(String deployment, String subdeployment, String messageServer, String type, String name) {
        if (deployment == null) {
            return build(messageServer, type, name);
        }
        return build(deployment, subdeployment, messageServer, type, name);
    }

    static String ejb3(String deployment, String subdeployment, String type, String name) {
        return build(deployment, subdeployment, type, name);
    }

    static String host(String name) {
        return build(HOST, name);
    }

    static String hostServer(String host, String server) {
        return build(host, server);
    }

    static String job(String deployment, String subdeployment, String name) {
        return build(JOB, deployment, subdeployment, name);
    }

    static String jmsBridge(String name) {
        return build(JMS_BRIDGE, name);
    }

    static String jpaStatistic(String deployment, String subdeployment, String persistenceUnit) {
        return build(deployment, subdeployment, persistenceUnit);
    }

    static String loggingProfile(String name) {
        return build(LOGGING, name);
    }

    static String messagingServer(String name) {
        return build(MESSAGING_SERVER, name);
    }

    static String webServer(String name) {
        return build(UNDERTOW_SERVER, name);
    }

    static String webListener(String name) {
        return build(UNDERTOW_RUNTIME_LISTENER, name);
    }

    /**
     * @param type must be one of "user" or "group"
     */
    static String principal(String type, String name) {
        return build(type, name);
    }

    static String resourceAdapter(String name) {
        return build(RESOURCE_ADAPTER, name);
    }

    static String restResource(String deployment, String subdeployment, String name) {
        return build(deployment, subdeployment, name);
    }

    static String role(String name) {
        return asId(name);
    }

    static String securityDomain(String name) {
        return build(SECURITY_DOMAIN, name);
    }

    static String serverGroup(String name) {
        return build(SERVER_GROUP, name);
    }

    static String serverGroupDeployment(String serverGroup, String name) {
        return build(serverGroup, name);
    }

    static String undertowApplicationSecurityDomain(String name) {
        return build(UNDERTOW_APP_SECURITY_DOMAIN, name);
    }

    static String undertowServer(String name) {
        return build(UNDERTOW_SERVER, name);
    }

    static String undertowServletContainer(String name) {
        return build(UNDERTOW_SERVLET_CONTAINER, name);
    }


    // ------------------------------------------------------ methods

    /**
     * Turns a label which can contain whitespace and upper/lower case characters into an all lowercase id separated
     * by "-".
     */
    static String asId(@NonNls String text) {
        Iterable<String> parts = Splitter
                .on(CharMatcher.whitespace().or(CharMatcher.is('-')))
                .omitEmptyStrings()
                .trimResults()
                .split(text);
        return stream(parts.spliterator(), false)
                .map(String::toLowerCase)
                .map(CharMatcher.javaLetterOrDigit()::retainFrom)
                .collect(joining("-"));
    }

    static String build(@NonNls String id, @NonNls String... additionalIds) {
        return build(id, '-', additionalIds);
    }

    static String build(@NonNls String id, char separator, @NonNls String... additionalIds) {
        if (Strings.emptyToNull(id) == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        List<String> ids = Lists.newArrayList(id);
        if (additionalIds != null) {
            for (String additionalId : additionalIds) {
                if (!Strings.isNullOrEmpty(additionalId)) {
                    ids.add(additionalId);
                }
            }
        }
        return ids.stream().map(Ids::asId).collect(joining(String.valueOf(separator)));
    }

    /**
     * Only available in GWT!
     */
    static String uniqueId() {
        return Document.get().createUniqueId();
    }
}
