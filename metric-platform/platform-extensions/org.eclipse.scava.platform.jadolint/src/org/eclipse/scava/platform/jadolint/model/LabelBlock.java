/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

/**
 *
 * @author blue
 */
public class LabelBlock {
    
    private String key;
    private String value;
    
    public LabelBlock(String line){
        String[] splitLine = line.split("=");
        
        key = splitLine[0];
        value = splitLine[1];
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
