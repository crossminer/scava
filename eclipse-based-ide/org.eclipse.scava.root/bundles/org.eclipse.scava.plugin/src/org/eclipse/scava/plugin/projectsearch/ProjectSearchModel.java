package org.eclipse.scava.plugin.projectsearch;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

public class ProjectSearchModel extends Model {
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public ProjectSearchModel(KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}
	
	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}
}
