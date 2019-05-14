/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author blue
 */
public class Run implements Instruction{
    
    private boolean execForm;
    
    private List<RunBlock> runBlocks = new ArrayList<>();
    
    public Run(String line){
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
            
            runBlocks.add(new RunBlock(blockBody.trim()));
            
        } else {
        
            regex = "\\s*&&\\s*";

            String[] splitLine = lineWithoutInstruction.split(regex);

            for(String s : splitLine)
                runBlocks.add(new RunBlock(s));
        
        }
    }

    public List<RunBlock> getRunBlocks() {
        return runBlocks;
    }

    public boolean isExecForm() {
        return execForm;
    }

    public void setRunBlocks(List<RunBlock> runs) {
        this.runBlocks = runs;
    }

    public void setExecForm(boolean execForm) {
        this.execForm = execForm;
    }
    
    public List<RunBlock> getAptGetInstallBlocks(){
        List<RunBlock> runBlocksInstall = new ArrayList<>();
        
        for(RunBlock rb : runBlocks){
            String exec = rb.getExecutable();
            if(exec.equals("sudo apt-get") || exec.equals("apt-get")){
                String params = rb.getParams();
                
                String[] paramsArray = params.split(" ");
                
                boolean containsInstall = Arrays.stream(paramsArray).anyMatch("install"::equals);
                
                if(containsInstall == true){
                        runBlocksInstall.add(rb);
                }
            }
        }
        
        return runBlocksInstall;
    }
    
    public List<RunBlock> getPipInstallBlocks(){
        List<RunBlock> runBlocksInstall = new ArrayList<>();
        
        for(RunBlock rb : runBlocks){
            String exec = rb.getExecutable();
            if(exec.equals("sudo pip") || exec.equals("pip")){
                String params = rb.getParams();
                
                String[] paramsArray = params.split(" ");
                
                boolean containsInstall = Arrays.stream(paramsArray).anyMatch("install"::equals);
                
                if(containsInstall == true){
                        runBlocksInstall.add(rb);
                }
            }
        }
        
        return runBlocksInstall;
    }
    
    public List<RunBlock> getApkUpgradeBlocks(){
        List<RunBlock> runBlocksInstall = new ArrayList<>();
        
        for(RunBlock rb : runBlocks){
            String exec = rb.getExecutable();
            if(exec.equals("sudo apk") || exec.equals("apk")){
                String params = rb.getParams();
                
                String[] paramsArray = params.split(" ");
                
                boolean containsInstall = Arrays.stream(paramsArray).anyMatch("upgrade"::equals);
                
                if(containsInstall == true){
                        runBlocksInstall.add(rb);
                }
            }
        }
        
        return runBlocksInstall;
    }
    
    public List<RunBlock> getApkAddBlocks(){
        List<RunBlock> runBlocksInstall = new ArrayList<>();
        
        for(RunBlock rb : runBlocks){
            String exec = rb.getExecutable();
            if(exec.equals("sudo apk") || exec.equals("apk")){
                String params = rb.getParams();
                
                String[] paramsArray = params.split(" ");
                
                boolean containsInstall = Arrays.stream(paramsArray).anyMatch("add"::equals);
                
                if(containsInstall == true){
                        runBlocksInstall.add(rb);
                }
            }
        }
        
        return runBlocksInstall;
    }
    
}
