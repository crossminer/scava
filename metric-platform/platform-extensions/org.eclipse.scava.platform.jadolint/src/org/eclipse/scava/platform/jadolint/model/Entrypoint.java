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
public class Entrypoint {
    
    private boolean execForm;
    
    private String executable;
    private List<String> params = new ArrayList<>();
    
    public Entrypoint(String line){
        
        String lineWithoutInstruction = line.split(" ", 2)[1];
        
        if (lineWithoutInstruction.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(lineWithoutInstruction);

            List<String> matches = new ArrayList<String>();
            while (m.find()) {
                matches.add(m.group(1));
            }

            for (int i = 0; i < matches.size(); i++) {
                if (i == 0) {
                    executable = matches.get(i);
                } else {
                    params.add(matches.get(i));
                }
            }
        } else {
            String[] parts = lineWithoutInstruction.split(" ");

            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    executable = parts[i];

                } else {
                    params.add(parts[i]);
                }
            }
        }
    }

    public String getExecutable() {
        return executable;
    }

    public List<String> getParams() {
        return params;
    }
    
    public boolean isExecForm() {
        return execForm;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
    
    public void setExecForm(boolean execForm) {
        this.execForm = execForm;
    }
}
