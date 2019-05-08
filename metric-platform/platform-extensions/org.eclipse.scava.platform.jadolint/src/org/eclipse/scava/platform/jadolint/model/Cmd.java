/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author blue
 */
public class Cmd implements Instruction{
    
    private boolean execForm;
    
    private String executable;
    private String params;
    
    public Cmd(String line){
        String lineWithoutInstruction = line.split(" ", 2)[1];
        
        String regex = "\\[.*?\\]";
          
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(lineWithoutInstruction);

        if(matcher.find()) {

            pattern = Pattern.compile("\".*?\"");
            matcher = pattern.matcher(lineWithoutInstruction);

            List<String> allMatches = new ArrayList<String>();

            while (matcher.find()) {
                allMatches.add(matcher.group());
            }
            
            String blockBody = "";

            for(String s : allMatches){
                blockBody = blockBody + " " + s.substring(1, s.length() - 1);
            }
            
            String[] splitLine = blockBody.trim().split(" ", 2);
        
            executable = splitLine[0].trim();
            params = splitLine[1].trim();
            
            execForm = true;
            
        } else {

            String[] splitLine = lineWithoutInstruction.split(" ");

            executable = splitLine[0].trim();
            params = splitLine[1].trim();
            
            execForm = false;
        }
    }
    
    public boolean isExecForm() {
        return execForm;
    }
    
    public void setExecForm(boolean execForm) {
        this.execForm = execForm;
    }
    
}
