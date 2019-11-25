package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

import org.eclipse.scava.crossflow.runtime.Job;

public class Technology extends Job {

	protected String techKey;
	protected String fileExt;

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

	@Override
	public String toString() {
		return "Techs (" + " techKey=" + techKey + " fileExt=" + fileExt + " jobId=" + jobId + " correlationId="
				+ correlationId + " destination=" + destination + ")";
	}

}