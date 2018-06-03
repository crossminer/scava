/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.harukizaemon.simian.AuditListener;
import com.harukizaemon.simian.Block;
import com.harukizaemon.simian.CheckSummary;
import com.harukizaemon.simian.Options;
import com.harukizaemon.simian.SourceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CodeListenerImpl implements AuditListener{
	ArrayList<String> files=new ArrayList<String>();
	ArrayList<String> clonedCode =new ArrayList<String>();
	int numFiles=0;
	int numBlock=0;
	long cumulatedTime=0;
	int duplicatedLine=0;
	
	private static final Logger logger=LoggerFactory.getLogger(CodeListenerImpl.class);
	
	public void block(Block code) {
		// TODO Auto-generated method stub
		
		if(!files.contains(code.getSourceFile().getFilename())) {
			files.add(code.getSourceFile().getFilename());
		}				
	}
	

	public void endCheck(CheckSummary sum) {
		// TODO Auto-generated method stub		
		numFiles=sum.getDuplicateFileCount();
		numBlock=sum.getDuplicateBlockCount();
		cumulatedTime+=sum.getProcessingTime();
		duplicatedLine=sum.getDuplicateLineCount();
		
		
		
	}

	public void endSet(String end) {
		// TODO Auto-generated method stub
		
		clonedCode.add(end);
	}

	public void error(File f, Throwable e) {
		// TODO Auto-generated method stub	
		if(!f.exists()) {
			e.getMessage();
		}
		
	}
	
	public void fileProcessed(SourceFile source) {
		// TODO Auto-generated method stub
		
	}

	public void startCheck(Options opt) {
		// TODO Auto-generated method stub
		
	}

	public void startSet(int start, String finger) {
		// TODO Auto-generated method stub
		
	}


	public ArrayList<String> getListFiles() {
		// TODO Auto-generated method stub	
		return files;
	}


	public ArrayList<String> getClonedCode() {
		// TODO Auto-generated method stub
		return clonedCode;
	}


	public int getNumClonedFiles() {
		// TODO Auto-generated method stub
		return numFiles;
	}


	public int getNumClonedBlock() {
		// TODO Auto-generated method stub
		return numBlock;
	}


	public long getTotalTime() {
		// TODO Auto-generated method stub
		return cumulatedTime;
	}


	public int getDuplicatedLines() {
		// TODO Auto-generated method stub
		return duplicatedLine;
	}

}
