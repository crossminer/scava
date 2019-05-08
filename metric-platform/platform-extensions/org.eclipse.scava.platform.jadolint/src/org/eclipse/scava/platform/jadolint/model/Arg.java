/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author blue
 */
public class Arg implements Instruction{
    
    private String name;
    private String defaultValue;
    
    public Arg(String line){
        String splitLine = line.split(" ", 2)[1];

        if(splitLine.contains("=")){
            String[] parts = splitLine.split("=");
            name = parts[0];
            defaultValue = parts[1];
        } else{
            name = splitLine;
        }
    }
    
}
