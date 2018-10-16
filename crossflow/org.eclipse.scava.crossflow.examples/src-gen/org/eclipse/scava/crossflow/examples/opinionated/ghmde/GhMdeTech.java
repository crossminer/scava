package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import org.eclipse.scava.crossflow.runtime.Job;

public class GhMdeTech extends Job {
	
	private static final long serialVersionUID = 4691727207678744755L;
	
	protected String fileExtension;
	
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	
}