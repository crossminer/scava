/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint;

import org.eclipse.scava.platform.jadolint.model.Add;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.model.Line;
import org.eclipse.scava.platform.jadolint.model.Run;
import org.eclipse.scava.platform.jadolint.rules.RunRules;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blue
 */
public class LineMerger {
    
    public void mergeLines(Dockerfile doc, File dockerFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dockerFile)));
        String line = null;
        String newLine = "";
        int lineCounter = 0;
        int lineNumber = -1;
        
        boolean hasHealthcheck = false;
        boolean isContinued = false;
        
        while ((line = reader.readLine()) != null) {
            lineCounter++;
            if(line.trim().startsWith("#"))
                continue;
            if (hasInstruction(line) && line.endsWith(" \\")) {
                //newLine = "";
                newLine += line;
                isContinued = true;
                lineNumber = lineCounter;
            } else if (line.endsWith(" \\") && isContinued) {
                newLine = newLine + " " + line;
            } else if (newLine != null && newLine.contains("HEALTHCHECK") && !hasHealthcheck) {
                newLine += line;
                hasHealthcheck = true;
            } else if (!hasInstruction(line) && !line.endsWith(" \\") && isContinued) {
                newLine = newLine + " " + line;
                newLine = newLine.replace(" \\", "");
                newLine = newLine.trim().replaceAll(" +", " ");
                //newLine += "\n";
                doc.addLine(new Line(newLine, lineNumber));
                newLine = "";
                isContinued = false;
            } else if (hasInstruction(line) && !line.endsWith(" \\")) {
                newLine += line.trim().replaceAll(" +", " ");
                //newLine += "\n";
                lineNumber = lineCounter;
                doc.addLine(new Line(newLine, lineNumber));
                newLine = "";
            }
        }
        reader.close();
    }
    
    private boolean hasInstruction(String line){
        
        if (line.contains("ADD") || line.contains("ARG") || line.contains("CMD") || line.contains("COPY") || line.contains("ENTRYPOINT") || line.contains("ENV") || line.contains("EXPOSE") || 
                line.contains("FROM") || line.contains("HEALTHCHECK") || line.contains("LABEL") || line.contains("MAINTAINER") || line.contains("ONBUILD") || line.contains("RUN") || 
                line.contains("SHELL") || line.contains("STOPSIGNAL") || line.contains("USER") || line.contains("VOLUME") || line.contains("WORKDIR")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args){
        
        try {
            LineMerger l = new LineMerger();
            Dockerfile doc = new Dockerfile();
            
            l.mergeLines(doc, new File("/Users/blue/Desktop/yeah/Dockerfile"));
            
            for(Line line : doc.getLines()){
                //System.out.println(line.getLine() + " " + line.getLineNumber() + " " + line.getInstruction());
                if(line.getInstruction().equals("ADD")){
                    Add a = new Add(line.getLine());
                    //RunRules r = new RunRules(new Run(line.getLine()));
                    //System.out.println(r.checkDL3003() + " " + line.getLineNumber());
                    System.out.println(a.getUser() + " " + a.getGroup() + " " + a.getSrc() + " " + a.getDest());
                }
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(LineMerger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
