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

import org.eclipse.crossmeter.repository.model.VcsRepository;

public abstract class AbstractVcsManager implements IVcsManager {
	
	@Override
	public VcsRepositoryDelta getDelta(VcsRepository repository, String startRevision) throws Exception {
		return getDelta(repository, startRevision, getCurrentRevision(repository));
	}
	
}
