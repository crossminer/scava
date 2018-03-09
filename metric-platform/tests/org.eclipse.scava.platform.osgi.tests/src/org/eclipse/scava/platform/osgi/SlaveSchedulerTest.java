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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.osgi.executors.SlaveScheduler;
import org.junit.Test;

import com.mongodb.Mongo;

public class SlaveSchedulerTest {

	@Test
	public void test() throws Exception {
		
		Mongo mongo = new Mongo();
		
		SlaveScheduler slave = new SlaveScheduler(mongo);
		
		List<String> projects = new ArrayList<String>(); 
		
		projects.add("Project1");
		projects.add("Project2");
		projects.add("Project3");
		projects.add("Project4");
		projects.add("Project5");
		
		slave.queueProjects(projects);

		slave.run();
		slave.run(); // Should print error
		slave.finish();
	}
}
