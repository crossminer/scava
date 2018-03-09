/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.jackson;

import java.util.Date;

import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class ExtendedJsonDeserialiser<T> extends JsonDeserializer<T> {

    protected static Date getDate(JsonNode node,
            DeserializationContext context, String field) {
        JsonNode n = node.get(field);
        Date date = null;
        if (n != null) {
            String value = n.asText();
            if (!"null".equals(value)) {
                date = context.parseDate(n.asText());
            }
        }
        return date;
    }
    
    protected static Date getDate(JsonNode node,
            DateTimeFormatter formatter, String field) {
        JsonNode n = node.get(field);
        Date date = null;
        if (n != null) {
            String value = n.asText();
            if (!"null".equals(value)) {
                date = formatter.parseDateTime(n.asText()).toDate();
            }
        }
        return date;
    }

    protected static Boolean getBoolean(JsonNode node, String field) {
        JsonNode n = node.get(field);
        if (n != null) {
            return n.asBoolean();
        }
        return null;
    }
    
    protected static Integer getInteger(JsonNode node, String field) {
        JsonNode n = node.path(field);
        return processInteger(n, null);
    }
    
    protected static Long getLong(JsonNode node, String field) {
        JsonNode n = node.path(field);
        return processLong(n, null);
    }

    protected static Integer getInteger(JsonNode node, String field,
            Integer defaultValue) {
        JsonNode n = node.path(field);
        return processInteger(n, defaultValue);
    }

    protected static Integer getInteger(JsonNode node, String parentField,
            String childField) {
        return getInteger(node, parentField, childField, null);
    }

    protected static Integer getInteger(JsonNode node, String parentField,
            String childField, Integer defaultValue) {
        JsonNode n = node.path(parentField).path(childField);
        return processInteger(n, defaultValue);
    }

    protected static String getText(JsonNode node, String field) {
        JsonNode n = node.path(field);
        return processString(n, null);
    }

    protected static String getText(JsonNode node, String parentField,
            String childField) {
        JsonNode n = node.path(parentField).path(childField);
        return processString(n, null);
    }

    private static String processString(JsonNode n, String defaultValue) {
        if (!n.isMissingNode()) {
            return n.asText();
        }
        return null;
    }

    private static Integer processInteger(JsonNode n, Integer defaultValue) {
        if (!n.isMissingNode()) {
            return n.asInt();
        }
        return defaultValue;
    }
    
    private static Long processLong(JsonNode n, Long defaultValue) {
        if (!n.isMissingNode()) {
            return n.asLong();
        }
        return defaultValue;
    }
}
