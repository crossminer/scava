package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

public class RepoTechPair extends Repository {

	protected Technology tech;

	public Technology getTech() {
		return tech;
	}

	public void setTech(Technology tech) {
		this.tech = tech;
	}

	public String toString() {
		return "RepoTechPair (" + " tech=" + tech + " getFullName()=" + getFullName() + " getHtmlUrl()=" + getHtmlUrl()
				+ " jobId=" + jobId + " correlationId=" + correlationId + " destination=" + destination + ")";
	}

}
