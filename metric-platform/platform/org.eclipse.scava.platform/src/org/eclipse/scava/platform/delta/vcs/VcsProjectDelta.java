/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.vcs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

public class VcsProjectDelta {
	
	protected List<VcsRepositoryDelta> repoDeltas = new ArrayList<VcsRepositoryDelta>();
	
	public VcsProjectDelta(Project project, Date date, IVcsManager vcsManager) throws Exception {
		for (VcsRepository repo : project.getVcsRepositories()) {
			String[] revs = vcsManager.getRevisionsForDate(repo, date);
			
			if (revs == null || revs.length == 0) continue;
			
			boolean foundNull = false;
			for (String r : revs) {
				if (r == null) {
					foundNull = true;
					break;
				}
			}
			if (foundNull) continue;
			
			repoDeltas.add(vcsManager.getDelta(repo, revs[0], revs[revs.length-1]));
		}
	}
	
	public List<VcsRepositoryDelta> getRepoDeltas() {
		return repoDeltas;
	}
	
	public void setRepoDeltas(List<VcsRepositoryDelta> repoDeltas) {
		this.repoDeltas = repoDeltas;
	}
}
