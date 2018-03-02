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

public class WorkingCopyCheckoutException extends Exception {
  private static final long serialVersionUID = 8393210027833758295L;
  private final VcsRepository repo;
  private final String revision;

  public WorkingCopyCheckoutException(VcsRepository repo, String revision, Throwable cause) {
    super("could not checkout revision of " + repo.getUrl() + " for revision " + revision, cause);
    this.repo = repo;
    this.revision = revision;
  }
  
  public VcsRepository getRepository() {
    return repo;
  }
  
  public String getRevision() {
    return revision;
  }
}
