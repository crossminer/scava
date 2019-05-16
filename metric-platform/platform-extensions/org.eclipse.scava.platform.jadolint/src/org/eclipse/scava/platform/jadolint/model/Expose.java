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
public class Expose implements Instruction {
    
    private int port;
    private String protocol;
    
    
    public Expose(String line){
        String splitLine = line.split(" ", 2)[1];
        
        if(splitLine.contains("/")){
            String[] parts = splitLine.split("/");
            port = Integer.parseInt(parts[0]);
            protocol = parts[1];
        } else
            port = Integer.parseInt(splitLine);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }
    
}
