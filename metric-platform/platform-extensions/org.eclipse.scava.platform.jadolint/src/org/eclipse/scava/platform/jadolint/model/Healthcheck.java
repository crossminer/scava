/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class Healthcheck {
    
    private String name;
    private List<String> options = new ArrayList<String>();
    private boolean none = false;
    private String command;
    
    public Healthcheck(String line){
        String splitLine = line.split(" ", 2)[1];
        
        if(splitLine.equalsIgnoreCase("NONE")){
            none = true;
            return;
        }
        
        //TODO parse CDM sourroundings with groups

    }
    
}
