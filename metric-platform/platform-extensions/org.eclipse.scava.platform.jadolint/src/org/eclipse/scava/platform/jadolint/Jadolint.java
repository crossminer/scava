/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint;

import org.eclipse.scava.platform.jadolint.model.Add;
import org.eclipse.scava.platform.jadolint.model.Cmd;
import org.eclipse.scava.platform.jadolint.model.Copy;
import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.model.Expose;
import org.eclipse.scava.platform.jadolint.model.From;
import org.eclipse.scava.platform.jadolint.model.Line;
import org.eclipse.scava.platform.jadolint.model.Maintainer;
import org.eclipse.scava.platform.jadolint.model.Run;
import org.eclipse.scava.platform.jadolint.model.Workdir;
import org.eclipse.scava.platform.jadolint.rules.AddRules;
import org.eclipse.scava.platform.jadolint.rules.CmdRules;
import org.eclipse.scava.platform.jadolint.rules.CopyRules;
import org.eclipse.scava.platform.jadolint.rules.ExposeRules;
import org.eclipse.scava.platform.jadolint.rules.FromRules;
import org.eclipse.scava.platform.jadolint.rules.MaintainerRules;
import org.eclipse.scava.platform.jadolint.rules.RunRules;
import org.eclipse.scava.platform.jadolint.rules.WorkdirRules;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blue
 */
public class Jadolint {
    
    public static void main(String[] args){
        try {
            if(args.length < 1){
                System.out.println("Please provide a Dockerfile");
                return;
            }
            
            String path = args[0];
            
            LineMerger l = new LineMerger();
            Dockerfile doc = new Dockerfile();
            
            doc.setPath(path);
            
            l.mergeLines(doc, new File(path));
            
            List<Violation> violations = new ArrayList<>();
            
            for(Line line : doc.getLines()){
                //System.out.println(line.getLine() + " " + line.getLineNumber() + " " + line.getInstruction());
                if(line.getInstruction().equals("ADD")){
                    AddRules addRules = new AddRules(new Add(line.getLine()));
                    violations = addRules.runAddRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("CMD")){
                    CmdRules cmdRules = new CmdRules(new Cmd(line.getLine()));
                    violations = cmdRules.runCmdRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("COPY")){
                    CopyRules copyRules = new CopyRules(new Copy(line.getLine()));
                    violations = copyRules.runCopyRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("EXPOSE")){
                    ExposeRules exposeRules = new ExposeRules(new Expose(line.getLine()));
                    violations = exposeRules.runExposeRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("FROM")){
                    FromRules fromRules = new FromRules(new From(line.getLine()));
                    violations = fromRules.runFromRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("MAINTAINER")){
                    MaintainerRules maintainerRules = new MaintainerRules(new Maintainer(line.getLine()));
                    violations = maintainerRules.runMaintainerRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("RUN")){
                    RunRules runRules = new RunRules(new Run(line.getLine()));
                    violations = runRules.runRunRules(doc, line.getLineNumber());
                }
                if(line.getInstruction().equals("WORKDIR")){
                    WorkdirRules workdirRules = new WorkdirRules(new Workdir(line.getLine()));
                    violations = workdirRules.runWorkdirRules(doc, line.getLineNumber());
                }
                if(!violations.isEmpty())
                    doc.addViolations(violations);
            }
            
            for(Violation v : doc.getViolations())
                System.out.println(v.getFileName() + " " + v.getLineNumber() + " " + v.getCode() + " " + v.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Jadolint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
