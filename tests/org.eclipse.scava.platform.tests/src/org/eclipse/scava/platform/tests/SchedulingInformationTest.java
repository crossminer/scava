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

import static org.junit.Assert.*;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.ProjectCollection;
import org.eclipse.scava.repository.model.SchedulingInformation;
import org.eclipse.scava.repository.model.SchedulingInformationCollection;
import org.junit.Test;

import com.mongodb.Mongo;

public class SchedulingInformationTest {

	@Test
	public void test() throws Exception {
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		
		SchedulingInformationCollection col = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation();
		
		String id = "foo";
		
		SchedulingInformation si = new SchedulingInformation();
		si.setWorkerIdentifier(id).setIsMaster(false).getCurrentLoad().add("foo");
		
		col.add(si);
		col.sync();
		
		si = col.findOneByWorkerIdentifier(id);
		
		assertEquals(1, si.getCurrentLoad().size());

		// Attempt to clear it
		si.getCurrentLoad().clear();
		si.setWorkerIdentifier(si.getWorkerIdentifier()); // FIXME: We have to force dirtying
		col.sync();
		
		si = col.findOneByWorkerIdentifier(id);
		assertEquals(0, si.getCurrentLoad().size());
		
		// Clean up
		si = col.findOneByWorkerIdentifier(id);
		col.remove(si);
		col.sync();
		
		mongo.close();
	}

}
