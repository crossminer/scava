/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.vcs.workingcopy.manager;

import org.eclipse.crossmeter.repository.model.VcsRepository;

public class WorkingCopyManagerUnavailable extends Exception {
  private static final long serialVersionUID = -4749729537006181987L;
  private final VcsRepository repo;

  public WorkingCopyManagerUnavailable(VcsRepository repo) {
    super("Working copy manager unavailable for " + repo);
    this.repo = repo;
  }
  
  public VcsRepository getRepository() {
    return repo;
  }
}
