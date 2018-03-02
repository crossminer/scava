/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;

import org.eclipse.scava.platform.ProjectRepositoryManager;
import org.eclipse.scava.repository.model.Project;
import org.junit.Test;

import com.mongodb.Mongo;

public class TestUniqueIdGen {

	@Test
	public void test() {
		Mongo mongo = null;
		try {
			mongo = new Mongo();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Failed to create mongo.");
		}

		ProjectRepositoryManager man = new ProjectRepositoryManager(mongo);
		
		String name = "test-project-please-ignore";
		Project p1 = new Project();
		p1.setName(name);
		p1.setShortName(man.generateUniqueId(p1));
		
		man.getProjectRepository().getProjects().add(p1);
		man.getProjectRepository().getProjects().sync();
		
		Project p2 = new Project();
		p2.setName(name);
		
		assertEquals(p1.getShortName()+"-1", man.generateUniqueId(p2));
		
		man.getProjectRepository().getProjects().remove(p1);
		man.getProjectRepository().getProjects().sync();
		
		mongo.close();
	}

}
