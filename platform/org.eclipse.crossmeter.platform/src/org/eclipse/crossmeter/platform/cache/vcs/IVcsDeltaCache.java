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
package org.eclipse.crossmeter.platform.cache.vcs;
import org.eclipse.crossmeter.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.crossmeter.repository.model.VcsRepository;


public interface IVcsDeltaCache {
	
	public VcsRepositoryDelta getCachedDelta(VcsRepository repository, String startVersion, String endVersion);
	
	public void putDelta(VcsRepository repository, String startVersion, String endVersion, VcsRepositoryDelta delta);
}
