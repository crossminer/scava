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

import java.io.File;
import java.util.List;

import org.eclipse.crossmeter.repository.model.VcsRepository;

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
