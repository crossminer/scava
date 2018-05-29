package org.eclipse.scava.business.dto;

import java.util.List;

public class CodeCloneResult {
	
	
	private List<String> codeLines;
	private int duplicatedLines;
	private long time;
	
	public int getDuplicatedLines() {
		return duplicatedLines;
	}
	public void setDuplicatedLines(int duplicatedLines) {
		this.duplicatedLines = duplicatedLines;
	}

	public List<String> getCodeLines() {
		return codeLines;
	}

	public void setCodeLines(List<String> codeLines) {
		this.codeLines = codeLines;
	}


	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
	

}

