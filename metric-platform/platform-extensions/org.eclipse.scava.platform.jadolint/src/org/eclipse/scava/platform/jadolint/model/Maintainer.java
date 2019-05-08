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
public class Maintainer {
    private String name;
    
    public Maintainer(String line){
        name = line.split(" ", 2)[1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
