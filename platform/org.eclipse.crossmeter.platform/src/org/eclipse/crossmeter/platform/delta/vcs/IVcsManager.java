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

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.repository.model.VcsRepository;

public interface IVcsManager {
	
	public boolean appliesTo(VcsRepository repository);
	
	public String getCurrentRevision(VcsRepository repository) throws Exception;
	
	public String getFirstRevision(VcsRepository repository) throws Exception;
	
	public int compareVersions(VcsRepository repository, String versionOne, String versionTwo) throws Exception;
	
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision) throws Exception;
	
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision, String endRevision) throws Exception;
	
	public String[] getRevisionsForDate(VcsRepository repository, Date date) throws Exception;
	
	public Date getDateForRevision(VcsRepository repository, String revision) throws Exception;
	
	public String getContents(VcsCommitItem file) throws Exception;
	
	public boolean validRepository(VcsRepository repository) throws Exception;
	
}
