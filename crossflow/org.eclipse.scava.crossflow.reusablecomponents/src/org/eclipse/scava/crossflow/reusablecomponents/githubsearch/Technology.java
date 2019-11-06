package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

import org.eclipse.scava.crossflow.runtime.Job;

public class Technology extends Job {

	private String techKey;
	private String fileExt;

	public String getTechKey() {
		return techKey;
	}

	public void setTechKey(String techKey) {
		this.techKey = techKey;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}