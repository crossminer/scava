/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.vcs.workingcopy.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.platform.vcs.svn.SvnUtil;
import org.eclipse.scava.platform.vcs.workingcopy.manager.Churn;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyCheckoutException;
import org.eclipse.scava.platform.vcs.workingcopy.manager.WorkingCopyManager;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class SvnWorkingCopyManager implements WorkingCopyManager {

  public SvnWorkingCopyManager() {
    // DO NOT REMOVE BECAUSE OSGI NEEDS IT
  }

  @Override
  public boolean appliesTo(VcsRepository repository) {
    return repository instanceof SvnRepository;
  }

//  @Override
  public void checkoutBroken(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      SvnUtil.setupLibrary();
      final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
      try {
          final SvnCheckout checkout = svnOperationFactory.createCheckout();
          checkout.setSingleTarget(SvnTarget.fromFile(workingDirectory));
          checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(repository.getUrl()), SVNRevision.create(Long.parseLong(revision))));
          checkout.run();
      } finally {
          svnOperationFactory.dispose();
      }
    } catch (NumberFormatException | SVNException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
  }
  
  @Override
  public void checkout(File workingDirectory, VcsRepository repository, String revision)
      throws WorkingCopyCheckoutException {
    try {
      // TODO: we'd rather use the SVNkit but that blocks indefinitely on loading classes (see above)
      Process p = Runtime.getRuntime().exec(new String[] { "svn", "checkout", "-r", revision, repository.getUrl(), workingDirectory.getAbsolutePath() });
      p.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new WorkingCopyCheckoutException(repository, revision, e);
    }
    
  }

  @Override
  public List<Churn> getDiff(File workingDirectory, String lastRevision) {
	List<Churn> result = new ArrayList<>();
	StringBuilder option = new StringBuilder("-c");
	if (lastRevision == null) {
	  lastRevision = "1";
	}
	option.append(lastRevision);
	try {
	  List<String> commandArgs = new ArrayList<>(Arrays.asList(new String[] { "svn", "diff", option.toString() }));
	  /* 
	   * this little workaround makes sure the indexes we get for the diffs is in the form
	   * workingCopyRoot+"/"+itemPath (relative - in the sense how I made it relative in the SVNManager)
	   */
//	  for (String path: workingDirectory.list()) {
//		  // I hate this!!! :(
//		  if (!path.contains(".DS_Store")) {
//			  commandArgs.add(path);
//		  }
//	  }
	  
	  ProcessBuilder pb = new ProcessBuilder(commandArgs);
	  pb.redirectErrorStream(true);
	  pb.directory(workingDirectory);
	  final Process p = pb.start();
	  
	  
	  //Process p = Runtime.getRuntime().exec(commandArgs.toArray(new String[0]), null, workingDirectory);
//	  Thread reader = new Thread() {
//		  public void run() {
			  try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				  String line;
				  String currentItem = "";
				  int linesAdded = 0;
				  int linesDeleted = 0;
				  while ((line = reader.readLine()) != null) {
					if (!line.isEmpty() && line.startsWith("Index:")) {
					  if (!currentItem.isEmpty()) {
					    String itemName = currentItem;
						if (!itemName.startsWith("/")) {
					      itemName = "/" + itemName;
					    }
						result.add(new Churn(itemName, linesAdded, linesDeleted));
						linesAdded = 0;
						linesDeleted = 0;
					  }
					  currentItem = line.split(":")[1].trim();
					  continue;
					} 
					if (line.matches("^\\+[^\\+].*")) {
					  linesAdded++;
					} else if (line.matches("^\\-[^\\-].*")) {
				      linesDeleted++;
					}
				  }
			  } catch (IOException e) {
				  throw new RuntimeException(e);
			  }
			  
//		  }
//	  };
//	  reader.start();
	  p.waitFor();
//	  reader.join();
	} catch (IOException | InterruptedException e) {
	  throw new RuntimeException(e);
	}
	return result;
  }
}
