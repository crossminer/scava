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
public class Volume {
    
    private String path;
    
    public Volume(String line){
        path = line.split(" ", 2)[1];
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
