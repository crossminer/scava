package org.eclipse.scava.crossflow.reusablecomponents.githubsearch;

public class RepoTechPair extends Repository{
	
	private Technology tech;

	public Technology getTech() {
		return tech;
	}

	public void setTech(Technology tech) {
		this.tech = tech;
	}
	
}
