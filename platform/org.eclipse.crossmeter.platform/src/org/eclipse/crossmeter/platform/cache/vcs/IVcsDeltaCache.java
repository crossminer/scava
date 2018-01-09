/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.cache.vcs;
import org.eclipse.crossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.crossmeter.repository.model.VcsRepository;


public interface IVcsDeltaCache {
	
	public VcsRepositoryDelta getCachedDelta(VcsRepository repository, String startVersion, String endVersion);
	
	public void putDelta(VcsRepository repository, String startVersion, String endVersion, VcsRepositoryDelta delta);
}
