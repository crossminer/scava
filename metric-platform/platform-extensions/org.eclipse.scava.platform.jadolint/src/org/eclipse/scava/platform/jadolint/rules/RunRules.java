/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.rules;

import org.eclipse.scava.platform.jadolint.model.Dockerfile;
import org.eclipse.scava.platform.jadolint.model.Run;
import org.eclipse.scava.platform.jadolint.model.RunBlock;
import org.eclipse.scava.platform.jadolint.violations.Violation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author blue
 */
public class RunRules implements Rule {
    
    private Run run;

    public boolean checkDL3001() {
        List<RunBlock> runBlocks = run.getRunBlocks();
        for(RunBlock rb : runBlocks) {
            switch(rb.getExecutable()) {

            case "shutdown":
            case "service":
            case "ps":
            case "free":
            case "top":
            case "kill":
            case "mount":
            case "ifconfig":
            case "nano":
            case "vim":
                return false;
            }
        }

        return true;
    }
    
    public boolean checkDL3003(){
        //String findCd = "(\\s|^)cd\\s";
        List<RunBlock> runBlocks = run.getRunBlocks();
        
        for(RunBlock rb : runBlocks){
            if(rb.getExecutable().equals("cd"))
                return false;
        }
        
        return true;
    }

    public boolean checkDL3004() {
        List<RunBlock> runBlocks = run.getRunBlocks();
        for(RunBlock rb: runBlocks) {
            if(rb.getExecutable().equals("sudo")) {
                return false;
            }
        }

        return true;
    }

    public boolean checkDL3005() {
        for(RunBlock rb: run.getRunBlocks()) {
            if (rb.getExecutable().equals("apt-get")) {
                String[] paramsArray = rb.getParams().split(" ");
                for (String param: paramsArray) {
                    if(param.equals("upgrade") || param.equals("dist-upgrade")) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    
    public boolean checkDL3008(){
        List<RunBlock> runBlocks = run.getAptGetInstallBlocks();
        
        for(RunBlock b : runBlocks){
            String params = b.getParams();

            String[] paramsArray = params.split(" ");

            if(!paramsArray[paramsArray.length - 1].contains("="))
                return false;
        }
        
        return true;
    }

    public boolean checkDL3009() {
        List<RunBlock> runBlocks = run.getRunBlocks();

        for(RunBlock rb: runBlocks) {
            String exec = rb.getExecutable();
            String param = rb.getParams();
            if(exec.equals("rm") && param.equals("-rf /var/lib/apt/lists/*")) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkDL3013(){
        List<RunBlock> runBlocks = run.getPipInstallBlocks();
        
        for(RunBlock b : runBlocks){
            String params = b.getParams();

            String[] paramsArray = params.split(" ");

            if(!paramsArray[paramsArray.length - 1].contains("=="))
                return false;
        }
        
        return true;
    }

    public boolean checkDL3014() {
        for(RunBlock rb : run.getAptGetInstallBlocks()) {
            String params = rb.getParams();
            if (!params.contains("install -y")) {
                return false;
            }
        }

        return true;
    }

    public boolean checkDL3015() {
        for (RunBlock rb : run.getAptGetInstallBlocks()) {
            String[] paramsArray = rb.getParams().split(" ");
            if (!paramsArray[paramsArray.length - 2].equals("--no-install-recommends")) {
                return false;
            }
        }

        return true;
    }
    
    public boolean checkDL3017(){
        List<RunBlock> runBlocks = run.getApkUpgradeBlocks();
        
        if(!runBlocks.isEmpty())
            return false;
        
        return true;
    }
    
    public boolean checkDL3018(){
        List<RunBlock> runBlocks = run.getApkAddBlocks();
        
        for(RunBlock b : runBlocks){
            String params = b.getParams();

            String[] paramsArray = params.split(" ");

                if(!paramsArray[paramsArray.length - 1].contains("="))
                    return false;
        }
        
        return true;
    }
    
    public boolean checkDL3019(){
        List<RunBlock> runBlocks = run.getApkAddBlocks();
        
        for(RunBlock b : runBlocks){
            String params = b.getParams();

            String[] paramsArray = params.split(" ");
            
            for(String s : paramsArray){
                if(s.equals("--no-cache"))
                    return true;
            }
            
            return false;
        }
        
        return true;
    }
    
    public List<Violation> runRunRules(Dockerfile doc, int lineNumber){
        List<Violation> violations = new ArrayList<>();
        if(this.checkDL3001() == false)
            violations.add(new Violation("DL3001", "For some bash commands it makes no sense running them in a Docker container like ssh, vim, shutdown, service, ps, free, top, kill, mount, ifconfig", doc.getPath(), lineNumber));
        if(this.checkDL3003() == false)
            violations.add(new Violation("DL3003", "Use WORKDIR to switch to a directory", doc.getPath(), lineNumber));
        if(this.checkDL3004() == false)
            violations.add(new Violation("DL3004", "Do not use sudo as it leads to unpredictable behavior. Use a tool like gosu to enforce root", doc.getPath(), lineNumber));
        if(this.checkDL3005() == false)
            violations.add(new Violation("DL3005", "Do not use apt-get upgrade or dist-upgrade", doc.getPath(), lineNumber));
        if(this.checkDL3008() == false)
            violations.add(new Violation("DL3008", "Pin versions in apt-get install", doc.getPath(), lineNumber));
        if(this.checkDL3009() == false)
            violations.add(new Violation("DL3009", "Delete the apt-get lists after installing something", doc.getPath(), lineNumber));
        if(this.checkDL3013() == false)
            violations.add(new Violation("DL3013", "Pin versions in pip", doc.getPath(), lineNumber));
        if(this.checkDL3014() == false)
            violations.add(new Violation("DL3014", "Use the -y switch", doc.getPath(), lineNumber));
        if(this.checkDL3015() == false)
            violations.add(new Violation("DL3015", "Avoid additional packages by specifying --no-install-recommends", doc.getPath(), lineNumber));
        if(this.checkDL3017() == false)
            violations.add(new Violation("DL3017", "Do not use apk upgrade", doc.getPath(), lineNumber));
        if(this.checkDL3018() == false)
            violations.add(new Violation("DL3018", "Pin versions in apk add. Instead of apk add <package> use apk add <package>=<version>", doc.getPath(), lineNumber));
        if(this.checkDL3019() == false)
            violations.add(new Violation("DL3019", "Use the --no-cache switch to avoid the need to use --update and remove /var/cache/apk/* when done installing packages", doc.getPath(), lineNumber));
        
        return violations;
    }
    
    public RunRules(Run run){
        this.run = run;
    }
}
