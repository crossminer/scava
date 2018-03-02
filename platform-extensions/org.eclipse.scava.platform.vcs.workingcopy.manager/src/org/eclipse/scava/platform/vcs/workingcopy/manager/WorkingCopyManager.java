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

import java.io.File;
import java.util.List;

import org.eclipse.scava.repository.model.VcsRepository;

public interface WorkingCopyManager {
  /**
   * @return true iff this working copy manager can manage to check out a working copy for this repository.
   */
  boolean appliesTo(VcsRepository repository);

  /**
   * Makes sure that after running this code in workingDirectory an up-to-date and 
   * clean check out is available.
   * 
   * @param workingdirectory  where to check out the working copy
   * @param repository        from where to get the code
   * @param revision 
   * @throws WorkingCopyCheckoutException in case something goes awry
   */
  void checkout(File workingDirectory, VcsRepository repository, String revision) throws WorkingCopyCheckoutException;
  
  /**
   * Calls a diff between the two provided revisions on the workingcopy provided.
   * Note: This is only intended to work with local working copies.
   * 
   * @param workingDirectory The target for the diff.
   * @param numberOfCommits The number of commits to fetch.
   * @return The entire diff stream between the two revisions as a string.
   */
  List<Churn> getDiff(File workingDirectory, String lastRevision);
}
