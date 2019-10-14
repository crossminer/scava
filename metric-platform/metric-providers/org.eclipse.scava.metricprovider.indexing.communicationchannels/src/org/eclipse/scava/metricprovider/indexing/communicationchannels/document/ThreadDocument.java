package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.ArrayList;
import java.util.List;

public class ThreadDocument extends DocumentAbstract {

	private String communication_channel_id;
	private String project_name;
	private String subject;
	private String thread_id;
	private MigrationIssue migration_issue;
	
	public ThreadDocument(String uid, String projectName, String collectionName, String threadId, String subject) {
		this.uid=uid;
		this.project_name=projectName;
		this.communication_channel_id = collectionName;
		this.thread_id = threadId;
		this.subject=subject;
	}

	public String getCommunication_channel_id() {
		return communication_channel_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public String getSubject() {
		return subject;
	}

	public String getThread_id() {
		return thread_id;
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
				problematic_changes=new ArrayList<ProblematicChange>();
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
