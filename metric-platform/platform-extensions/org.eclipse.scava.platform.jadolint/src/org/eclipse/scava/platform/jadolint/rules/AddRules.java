/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.rules;

import org.eclipse.scava.platform.jadolint.model.Add;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class AddRules implements Rule {
    private Add add;
    
    public boolean checkDL3020(){
        //String findCd = "(\\s|^)cd\\s";
        
        List<String> src = add.getSrc();
        
        for(String s : src){
            if(!s.contains(".tar"))
                return false;
        }
        
        return true;
    }
    
    public List<Violation> runAddRules(Dockerfile doc, int lineNumber){
        List<Violation> violations = new ArrayList<>();
        if(this.checkDL3020() == false)
            violations.add(new Violation("DL3020", "Use COPY instead of ADD for files and folders", doc.getPath(), lineNumber));
        
        return violations;
    }
    
    public AddRules(Add add){
        this.add = add;
    }
    
}
