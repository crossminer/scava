/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
