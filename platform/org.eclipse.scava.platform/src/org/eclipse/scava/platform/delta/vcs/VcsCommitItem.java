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

import java.io.Serializable;

public class VcsCommitItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String path;
	protected VcsChangeType changeType;
	protected VcsCommit commit;
	
	public VcsCommit getCommit() {
		return commit;
	}
	
	public void setCommit(VcsCommit commit) {
		this.commit = commit;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public VcsChangeType getChangeType() {
		return changeType;
	}
	
	public void setChangeType(VcsChangeType changeType) {
		this.changeType = changeType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VcsCommitItem) {
			if (!this.path.equals(((VcsCommitItem) obj).getPath())) {
				return false;
			} 
			if (!this.changeType.equals(((VcsCommitItem) obj).getChangeType())) {
				return false;
			}
			if (!this.commit.equals(((VcsCommitItem) obj).getCommit())) {
				return false; // FIXME: only matching at the object level currently. BAD.
			}
			return true;
		}
		
		return false;
	}
	
	
}
