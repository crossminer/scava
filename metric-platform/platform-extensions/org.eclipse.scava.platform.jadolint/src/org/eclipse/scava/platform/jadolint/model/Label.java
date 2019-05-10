/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue
 */
public class Label {
    
    List<LabelBlock> labelBlocks = new ArrayList<>();
    
    public Label(String line){
        String lineWithoutInstruction = line.split(" ", 2)[1];
        
        String[] splitLine = lineWithoutInstruction.split(" ");
        
        for(String s : splitLine)
            labelBlocks.add(new LabelBlock(s));
    }
    
}
