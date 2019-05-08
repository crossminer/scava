/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform;

import java.util.Iterator;

import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class ProjectRepositoryManager {
	
	protected ProjectRepository projectRepository = null;
	protected final String projectsDatabaseName = "scava";
	protected DB db;
	protected Mongo mongo;
	
	public ProjectRepositoryManager(Mongo mongo) {
		this.mongo = mongo;
		init();
	}
	
	protected void init() {
		this.db = mongo.getDB(projectsDatabaseName);
		projectRepository = new ProjectRepository(db);
	}
	
	public ProjectRepository getProjectRepository() {
		return projectRepository;
	}
	
	public DB getDb() {
		return db;
	}
	
	public boolean exists(String projectName){
		//FIXME: Not a strong enough test
		if(projectRepository.getProjects().findOneByName(projectName) !=null) return true;
		return false;
	}
	
	public void reset() {
		mongo.dropDatabase(projectsDatabaseName);
		init();
	}
	
	public String generateUniqueId(Project project) {
		String desired = project.getName().replaceAll("[^A-Za-z0-9]", "");
		
		Iterator<Project> it = projectRepository.getProjects().findByName(project.getName()).iterator();
		if (!it.hasNext()){
			return desired;
		} else {
			int last = 0;
			while (it.hasNext()) {
				Project alt = it.next();
				if (alt.getShortName().contains("-")) {
					int id = Integer.valueOf(alt.getShortName().split("-")[1]);
					if (id > last) last = id;
				}
			}
			return desired + "-" + (last+1);
		}
	}
}
