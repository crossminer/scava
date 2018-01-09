/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.delta.vcs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.crossmeter.repository.model.VcsRepository;

public class VcsRepositoryDelta implements Serializable {
	
	transient protected VcsRepository repository;
	protected List<VcsCommit> commits = new ArrayList<VcsCommit>();
	protected String latestRevision;
	
	public VcsRepository getRepository() {
		return repository;
	}
	
	public void setRepository(VcsRepository repository) {
		this.repository = repository;
	}
	
	public List<VcsCommit> getCommits() {
		return commits;
	}
	
	public String getLatestRevision() {
		return latestRevision;
	}
	
	public void setLatestRevision(String latestRevision) {
		this.latestRevision = latestRevision;
	} //TODO THIS NEEDS SETTING ON CREATION
	
	public List<VcsCommitItem> getCompactedCommitItems() {
		List<VcsCommitItem> commitItems = new ArrayList<VcsCommitItem>();
		
		for (VcsCommit commit : commits) {
			for (VcsCommitItem item : commit.getItems()) {
				switch (item.getChangeType()) {
					case ADDED:
						commitItems.add(item);
						break;
					case DELETED:
						removeCommitItemByPath(commitItems, item.getPath());
						break;
					case UPDATED:
					case REPLACED:
						removeCommitItemByPath(commitItems, item.getPath());
						commitItems.add(item);
						break;
					case UNKNOWN:
						//ignore
						System.err.println("Found unknnown commit kind: " + item.getChangeType());
						break;
				}
			}
		}
		
		return commitItems;
	}
	
	// TODO: Is the path enough?
	protected void removeCommitItemByPath(List<VcsCommitItem> items , String path) {
		Iterator<VcsCommitItem> it = items.iterator();
		while (it.hasNext()) {
			VcsCommitItem item = it.next();
			if (item.getPath().equals(path)) it.remove();
		}
	}
	
}
