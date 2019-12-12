/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.bugs.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BugDocument extends DocumentAbstract {

	private Date created_at;
	private String subject;
	private String severity;
	private String bug_id;
	private String project_name;
	private String creator;
	private MigrationIssue migration_issue;
	

	public BugDocument(String uid, String bugID, String projectName, String summary, Date created_at, String creator) {
		this.uid = uid;
		this.bug_id = bugID;
		this.project_name = projectName;
		this.subject = summary;
		this.created_at = created_at;
		this.creator = creator;
	}

	public String getProjectName() {
		return project_name;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @return the created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getBug_id() {
		return bug_id;
	}

	public String getProject_name() {
		return project_name;
	}
	
	public void setMigration_issue(boolean found)
	{
		migration_issue=new MigrationIssue(found);
	}
	
	public MigrationIssue getMigration_issue() {
		return migration_issue;
	}
	
	public void addProblematic_change(String change, Double matching_score)
	{
		if(migration_issue==null)
			migration_issue=new MigrationIssue(true);
		migration_issue.addProblematicChange(change, matching_score);
	}
	
	private class MigrationIssue
	{
		private boolean found;
		List<ProblematicChange> problematic_changes;
		
		public MigrationIssue(boolean found) {
			this.found=found;
		}
		
		public void addProblematicChange(String change, Double matching_score)
		{
			if(problematic_changes==null)
			{
				problematic_changes=new ArrayList<BugDocument.MigrationIssue.ProblematicChange>();
			}
			problematic_changes.add(new ProblematicChange(change, matching_score));
		}
		
		public List<ProblematicChange> getProblematic_changes() {
			return problematic_changes;
		}
		
		public boolean getFound()
		{
			return found;
		}
		
		private class ProblematicChange
		{
			String change;
			Double matching_score;
			
			public ProblematicChange(String change, Double matching_score) {
				this.change = change;
				this.matching_score = matching_score;
			}
			
			public Double getMatching_score() {
				return matching_score;
			}
			
			public String getChange() {
				return change;
			}
		}
	}
}
