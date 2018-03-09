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

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.SchedulingInformation;

import com.mongodb.Mongo;

//FIXME: Move to its own project.
public class ExampleProjectCreator {

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName("Foo");
		project.setShortName("Foo");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Bar");
		project.setShortName("Bar");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Whizz");
		project.setShortName("Whizz");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Baz");
		project.setShortName("Baz");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		project = new Project();
		project.setName("Boop");
		project.setShortName("Boop");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);

		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(new SchedulingInformation());
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		mongo.close();
	}
}
