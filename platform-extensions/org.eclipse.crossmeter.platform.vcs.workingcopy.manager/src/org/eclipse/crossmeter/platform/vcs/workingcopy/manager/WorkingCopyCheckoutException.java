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
