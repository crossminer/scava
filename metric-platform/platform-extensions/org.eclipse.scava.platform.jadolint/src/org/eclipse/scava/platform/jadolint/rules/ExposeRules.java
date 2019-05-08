/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.rules;

import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.model.Expose;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class ExposeRules implements Rule {
    
    private Expose expose;
    
    public boolean checkDL3011(){      
        if(expose.getPort() >= 0 && expose.getPort() <= 65535)
            return true;
        else
            return false;
    }
    
    public List<Violation> runExposeRules(Dockerfile doc, int lineNumber){
        List<Violation> violations = new ArrayList<>();
        if(this.checkDL3011() == false)
            violations.add(new Violation("DL3011", "Valid UNIX ports range from 0 to 65535", doc.getPath(), lineNumber));
        
        return violations;
    }
    
    public ExposeRules(Expose expose){
        this.expose = expose;
    }
    
}
