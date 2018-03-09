/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.zendesk.model.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stephenc
 * @since 05/04/2013 11:56
 */
public class CreateEvent extends Event {
    private String fieldName;
    private List<String> value;

    @JsonProperty("field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JsonProperty("value")
    public Object getValueObject() {
        if (value == null) {
            return null;
        }
        if (value.size() == 1) {
            return value.get(0);
        }
        return value;
    }

    public void setValueObject(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof List) {
            this.value = new ArrayList<String>();
            for (Object o : (List) value) {
                this.value.add(o == null || o instanceof String ? (String) o : o.toString());
            }
        } else if (value instanceof String[]) {
            this.value = new ArrayList<String>();
            for (String s : (String[]) value) {
                this.value.add(s);
            }
        } else if (value instanceof Object[]) {
            this.value = new ArrayList<String>();
            for (Object o : (Object[]) value) {
                this.value.add(o == null || o instanceof String ? (String) o : o.toString());
            }
        } else if (value instanceof String) {
            setValue((String) value);
        } else {
            setValue(value.toString());
        }
    }

    @JsonIgnore
    public List<String> getValues() {
        return value;
    }

    public void setValues(List<String> value) {
        this.value = value;
    }

    @JsonIgnore
    public String getValue() {
        return value == null || value.size() != 1 ? null : value.get(0);
    }

    public void setValue(String value) {
        if (value == null) {
            this.value = null;
        } else {
            this.value = new ArrayList<String>();
            this.value.add(value);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateEvent");
        sb.append("{fieldName='").append(fieldName).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
