/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.vcs.workingcopy.manager;

import org.eclipse.scava.repository.model.VcsRepository;

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
