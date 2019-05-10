package org.eclipse.scava.platform.jadolint.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add {
    private List<String> src = new ArrayList<>();
    private String dest;
    private String user;
    private String group;

    public Add(String line) {
        String splitLine = line.split(" ", 2)[1]; //ADD command discarded
        
        if(splitLine.contains("[\"")){
            
            if (splitLine.startsWith("--chown")) {
                String[] parts = splitLine.split(" ", 2);
                if (parts[0].contains(":")) {
                    user = parts[0].substring(parts[0].indexOf("=") + 1,
                                    parts[0].indexOf(":"));
                    group = parts[0].substring(parts[0].indexOf(":") + 1);
                } else {
                    user = parts[0].substring(parts[0].indexOf("=") + 1,
                                    parts[0].length());
                }
                
                String locations = parts[1];
                
                String regex = "\\[.*?\\]";
          
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(locations);
                
                List<String> allMatches = new ArrayList<>();

                if(matcher.find()) {

                    pattern = Pattern.compile("\".*?\"");
                    matcher = pattern.matcher(locations);

                    while (matcher.find()) {
                        allMatches.add(matcher.group());
                    }

                    for(int i = 0; i < allMatches.size() - 1; i++){
                        src.add(allMatches.get(i).replace("\"", ""));
                    }
                    
                    dest = allMatches.get(allMatches.size() - 1).replace("\"", "");
                }
            } else {
                String regex = "\\[.*?\\]";
          
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(splitLine);

                if(matcher.find()) {

                    pattern = Pattern.compile("\".*?\"");
                    matcher = pattern.matcher(splitLine);

                    List<String> allMatches = new ArrayList<>();

                    while (matcher.find()) {
                        allMatches.add(matcher.group());
                    }

                    for(int i = 0; i < allMatches.size() - 1; i++){
                        src.add(allMatches.get(i).replace("\"", ""));
                    }
                    
                    dest = allMatches.get(allMatches.size() - 1).replace("\"", "");
                }
            }
            
            
            
        } else {
        
            if (splitLine.startsWith("--chown")) {
                String[] parts = splitLine.split(" ");
                if (parts[0].contains(":")) {
                    user = parts[0].substring(parts[0].indexOf("=") + 1,
                                    parts[0].indexOf(":"));
                    group = parts[0].substring(parts[0].indexOf(":") + 1);
                } else {
                    user = parts[0].substring(parts[0].indexOf("=") + 1,
                                    parts[0].length());
                }
                for(int i = 1; i < parts.length - 1; i++){
                    src.add(parts[i]);
                }
                dest = parts[parts.length - 1];
            } else {
                if (splitLine.contains(" ")) { //first form
                    String[] parts = splitLine.split(" ");
                    for(int i = 0; i < parts.length - 1; i++){
                        src.add(parts[i]);
                    }
                    dest = parts[parts.length - 1];
                }
            }
        }
    }

    public List<String> getSrc() {
        return src;
    }

    public void setSrc(List<String> src) {
        this.src.addAll(src);
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
