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
public class From {
    
    private String image;
    private String tag;
    private String digest;
    private String alias;
    
    public From(String line){
        String splitLine = line.split(" ", 2)[1];
        
        String[] params = splitLine.split(" +");
        
        if(params.length > 1){
            if(params[0].contains(":")){
                String[] parts = params[0].split(":");
                image = parts[0];
                tag = parts[1];
            } else if(params[0].contains("@")){
                String[] parts = params[0].split("@");
                image = parts[0];
                digest = parts[1];
            } else{
                image = params[0];
            }
            alias = params[2];
        } else if(splitLine.contains(":")){
            String[] parts = splitLine.split(":");
            image = parts[0];
            tag = parts[1];
        } else if(splitLine.contains("@")){
            String[] parts = splitLine.split("@");
            image = parts[0];
            digest = parts[1];
        } else{
            image = splitLine;
        }
        
        
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    public String getDigest() {
        return digest;
    }

    public String getAlias() {
        return alias;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
