/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.osgi;

import java.text.ParseException;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.NoManagerFoundException;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.Mongo;

public class ProjectSyncTest {
	public static void main(String[] args) throws Exception{
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().first();
		
		project.getExecutionInformation().setInErrorState(false);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		Date date = getLastExecutedDate(platform, project);
		System.out.println(date);
		
		
		ProjectDelta delta = new ProjectDelta(project, date, platform);
		
		try {
			delta.create();
		} catch (Exception e) {
			project.getExecutionInformation().setInErrorState(true);
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			
			System.err.println("Project delta creation failed. Aborting.");
			return;
		}
		
		if (project.getExecutionInformation().getInErrorState()) {
			// TODO: what should we do? Is the act of not-updating the lastExecuted flag enough?
			// If it continues to loop, it simply tries tomorrow. We need to stop this happening.
			System.err.println("Project in error state. Stopping execution.");
		} else {
			System.out.println("Updating last executed date."); //FIXME: This is not persisting to the database. 
			project.getExecutionInformation().setLastExecuted(date.toString());
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
	}
	
	
	protected static Date getLastExecutedDate(Platform platform, Project project) {
		Date lastExec;
		String lastExecuted = project.getExecutionInformation().getLastExecuted();
		
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date();
			
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1)) 
				try {
					Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					} 
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				try {
					Date d = platform.getCommunicationChannelManager().getFirstDate(platform.getMetricsRepository(project).getDb(), communicationChannel);
					if (d == null) continue;
					d = d.addDays(-1);
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				try {
					Date d = platform.getBugTrackingSystemManager().getFirstDate(platform.getMetricsRepository(project).getDb(), bugTrackingSystem).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				lastExec = new Date(lastExecuted);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return lastExec;
	}
}
