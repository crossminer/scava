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
public class Stopsignal {
    private String signal;
    
    public Stopsignal(String line){
        signal = line.split(" ", 2)[1];
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}
