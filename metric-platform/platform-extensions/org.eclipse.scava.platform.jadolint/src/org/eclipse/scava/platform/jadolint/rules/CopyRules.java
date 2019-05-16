/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.rules;

import org.eclipse.scava.platform.jadolint.model.Copy;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.model.From;
import org.eclipse.scava.platform.jadolint.model.Line;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class CopyRules implements Rule {
    private Copy copy;
    
    public boolean checkDL3010(){
        
        List<String> src = copy.getSrc();
        
        for(String s : src){
            if(s.contains(".tar"))
                return false;
        }
        
        return true;
    }
    
    public boolean checkDL3021(){
        
        List<String> src = copy.getSrc();
        
        if(src.size() >= 2){
            if(!copy.getDest().endsWith("/"))
                return false;
        }
        
        return true;
    }
    
    public boolean checkDL3022(Dockerfile doc){
        
        List<String> src = copy.getSrc();
        
        for(String s : src){
            if(s.contains("--from")) {
                String alias = s.split("=")[1];
                for(Line line : doc.getLines()){
                    if(line.getInstruction().equals("FROM")){
                        From from = new From(line.getLine());
                        if(from.getAlias().equals(alias)){
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        
        return true;
    }
    
    public boolean checkDL3023(Dockerfile doc, int lineNumber){
        
        Line line = null;
        
        List<String> src = copy.getSrc();
        
        for(String s : src){
            if(s.contains("--from")) {
                String alias = s.split("=")[1];
                for(Line l : doc.getLines()){
                    if(l.getLineNumber() > lineNumber)
                        break;
                    if(l.getInstruction().equals("FROM")){
                        line = l;
                    }
                }

                if(line != null && new From(line.getLine()).getAlias() != null && new From(line.getLine()).getAlias().equals(alias))
                    return false;
            }
        }
        
        return true;
    }
    
    public List<Violation> runCopyRules(Dockerfile doc, int lineNumber){
        List<Violation> violations = new ArrayList<>();
        if(this.checkDL3010() == false)
            violations.add(new Violation("DL3010", "Use ADD for extracting archives into an image", doc.getPath(), lineNumber));
        if(this.checkDL3021() == false)
            violations.add(new Violation("DL3021", "COPY with more than 2 arguments requires the last argument to end with /", doc.getPath(), lineNumber));
        if(this.checkDL3022(doc) == false)
            violations.add(new Violation("DL3022", "COPY --from should reference a previously defined FROM alias", doc.getPath(), lineNumber));
        if(this.checkDL3023(doc, lineNumber) == false)
            violations.add(new Violation("DL3023", "COPY --from cannot reference its own FROM alias", doc.getPath(), lineNumber));
        
        return violations;
    }
    
    public CopyRules(Copy copy){
        this.copy = copy;
    }
    
}
