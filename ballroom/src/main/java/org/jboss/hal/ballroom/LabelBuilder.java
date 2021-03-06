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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.jboss.hal.dmr.Property;

import static org.jboss.hal.dmr.ModelDescriptionConstants.HAL_LABEL;

/** Generates human readable labels from terms used in the management model. */
public class LabelBuilder {

    private static final String QUOTE = "'";
    @SuppressWarnings("HardCodedStringLiteral")
    private final ImmutableMap<String, String> SPECIALS = ImmutableMap.<String, String>builder()
            .put("ajp", "AJP")
            .put("dn", "DN")
            .put("ee", "EE")
            .put("ejb3", "EJB3")
            .put("http", "HTTP")
            .put("http2", "HTTP/2")
            .put("jacc", "JACC")
            .put("jaxrs", "JAX-RS")
            .put("jdbc", "JDBC")
            .put("jca", "JCA")
            .put("jdr", "JDA")
            .put("jgroups", "JGroups")
            .put("jmx", "JMX")
            .put("jndi", "JNDI")
            .put("jpa", "JPA")
            .put("jsf", "JSF")
            .put("jsr", "JSR")
            .put("jwt", "JWT")
            .put("oauth2", "OAuth 2")
            .put("otp", "OTP")
            .put("rdn", "RDN")
            .put("sar", "SAR")
            .put("sasl", "SASL")
            .put("sql", "SQL")
            .put("ssl", "SSL")
            .put("tcp", "TCP")
            .put("ttl", "TTL")
            .put("uri", "URI")
            .put("url", "URL")
            .put("wsdl", "WSDL")
            .build();

    public String label(final Property property) {
        return property.getValue().hasDefined(HAL_LABEL)
                ? label(property.getValue().get(HAL_LABEL).asString())
                : label(property.getName());
    }

    public String label(final String name) {
        String label = name;
        label = label.replace('-', ' ');
        label = replaceSpecial(label);
        label = capitalize(label);
        return label;
    }

    /**
     * Turns a list of names from the management model into a human readable enumeration wrapped in quotes and
     * separated with commas. The last name is separated with the specified conjunction.
     *
     * @return The list of names as human readable string or an empty string if the names are null or empty.
     */
    public String enumeration(final Iterable<String> names, final String conjunction) {
        String enumeration = "";
        if (names != null && !Iterables.isEmpty(names)) {
            int size = Iterables.size(names);
            if (size == 1) {
                return QUOTE + label(names.iterator().next()) + QUOTE;
            } else if (size == 2) {
                return QUOTE + label(Iterables.getFirst(names, "")) + QUOTE +
                        " " + conjunction + " " +
                        QUOTE + label(Iterables.getLast(names)) + QUOTE;
            } else {
                String last = Iterables.getLast(names);
                LinkedList<String> allButLast = new LinkedList<>();
                Iterables.addAll(allButLast, names);
                allButLast.removeLast();
                enumeration = allButLast.stream()
                        .map(name -> QUOTE + label(name) + QUOTE)
                        .collect(Collectors.joining(", "));
                enumeration = enumeration + " " + conjunction + " " + QUOTE + label(last) + QUOTE;
            }
        }
        return enumeration;
    }

    private String replaceSpecial(final String label) {
        List<String> replacedParts = new ArrayList<>();
        for (String part : Splitter.on(' ').split(label)) {
            String replaced = part;
            for (Map.Entry<String, String> entry : SPECIALS.entrySet()) {
                if (replaced.length() == entry.getKey().length()) {
                    replaced = replaced.replace(entry.getKey(), entry.getValue());
                }
            }
            replacedParts.add(replaced);
        }
        return Joiner.on(" ").join(replacedParts);
    }

    private String capitalize(final String str) {
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toUpperCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }
}
