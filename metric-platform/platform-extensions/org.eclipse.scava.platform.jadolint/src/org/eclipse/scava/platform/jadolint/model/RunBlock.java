/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.List;

/**
 *
 * @author blue
 */
public class RunBlock {
    
    private String executable;
    private String params;
    
    public RunBlock(String line){
        
        if(line.trim().split(" ", 2)[0].equals("sudo")){
            String lineWithoutSudo = line.split(" ", 2)[1];
            
            String[] splitLine = lineWithoutSudo.split(" ", 2);

            executable = "sudo " + splitLine[0].trim();
            params = splitLine[1].trim();
        } else if(line.trim().contains(" ")){
            String[] splitLine = line.split(" ", 2);

            executable = splitLine[0].trim();
            params = splitLine[1].trim();
        } else {
            executable = line;
        }
        
    }

    public String getExecutable() {
        return executable;
    }

    public String getParams() {
        return params;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public void setParams(String params) {
        this.params = params;
    }
    
}
