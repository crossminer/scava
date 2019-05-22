/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.rules;

import org.eclipse.scava.platform.jadolint.model.Cmd;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class CmdRules implements Rule {
    private Cmd cmd;
    
    public boolean checkDL3025(){
        
        if(!cmd.isExecForm())
            return false;
        
        return true;
    }
    
    public List<Violation> runCmdRules(Dockerfile doc, int lineNumber){
        List<Violation> violations = new ArrayList<>();
        if(this.checkDL3025() == false)
            violations.add(new Violation("DL3025", "Use arguments JSON notation for CMD and ENTRYPOINT arguments", doc.getPath(), lineNumber));
        
        return violations;
    }
    
    public CmdRules(Cmd cmd){
        this.cmd = cmd;
    }
}
