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
		numFiles=sum.getDuplicateFileCount();
		numBlock=sum.getDuplicateBlockCount();
		cumulatedTime+=sum.getProcessingTime();
		duplicatedLine=sum.getDuplicateLineCount();
	}

	public void endSet(String end) {
		clonedCode.add(end);
	}

	public void error(File f, Throwable e) {
		if(!f.exists()) {
			e.getMessage();
		}
		
	}
	
	public void fileProcessed(SourceFile source) {
		
	}

	public void startCheck(Options opt) {
		
	}

	public void startSet(int start, String finger) {
		
	}


	public ArrayList<String> getListFiles() {
		return files;
	}


	public ArrayList<String> getClonedCode() {
		return clonedCode;
	}


	public int getNumClonedFiles() {
		return numFiles;
	}


	public int getNumClonedBlock() {
		return numBlock;
	}


	public long getTotalTime() {
		return cumulatedTime;
	}


	public int getDuplicatedLines() {
		return duplicatedLine;
	}

}
