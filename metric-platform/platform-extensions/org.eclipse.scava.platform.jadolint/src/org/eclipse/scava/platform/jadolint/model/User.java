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
public class User {
    private String user;
    private String group;
    
    
    public User(String line){
        String splitLine = line.split(" ", 2)[1];
        
        if(splitLine.contains("/")){
            String[] parts = splitLine.split("/");
            user = parts[0];
            group = parts[1];
        } else
            user = splitLine;
    }

    public String getUser() {
        return user;
    }

    public String getGroup() {
        return group;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
