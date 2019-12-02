package org.eclipse.scava.platform.documentation.gitbased.utils;

import java.util.List;

import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;

public class VcsDocumentationDelta extends VcsRepositoryDelta {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 870352631270149432L;

	
	public void setCommits(List<VcsCommit> commits)
	{
		this.commits=commits;
	}
}
