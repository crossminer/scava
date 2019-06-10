/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde.xtext;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger;

public class RepositoryCloner extends RepositoryClonerBase {
	
	@Override
	public RepositoryClone consumeRepositorySearchResults(Repository repository) throws Exception {
		
		// do not update a repo if folder already exists -- for development
		final boolean replace = false;
		
		RepositoryClone repositoryCloneInst = new RepositoryClone();
		
		final String CLONE_SOURCE = repository.getUrl() + ".git";

		final File CLONE_PARENT_DESTINATION = GhmdeProperties.CLONE_PARENT_DESTINATION;

		final File CLONE_REPO_DESTINATION = new File(CLONE_PARENT_DESTINATION + File.separator
				+ createUniqueFolderForRepo(repository.getName(), repository.getUrl()));

		//
		workflow.log(CrossflowLogger.SEVERITY.INFO, "cloning: " + CLONE_SOURCE);
		workflow.log(CrossflowLogger.SEVERITY.INFO, "into: " + CLONE_REPO_DESTINATION);
		//

		String clonedRepoLocalPath = CLONE_REPO_DESTINATION.getPath();
		
		repositoryCloneInst.setFileExt( repository.getFileExt() );
		repositoryCloneInst.setTechKey( repository.getTechKey() );
		repositoryCloneInst.setUrl( repository.getUrl() );
		repositoryCloneInst.setName( repository.getName() );
		repositoryCloneInst.setLocalPath(clonedRepoLocalPath);
		
		try {
			// clean local clone parent destination if it exists
			if (CLONE_PARENT_DESTINATION.exists()) {
				if (replace) {
					FileUtils.deleteDirectory(CLONE_PARENT_DESTINATION);
					workflow.log(CrossflowLogger.SEVERITY.INFO, "Successfully cleaned local clone parent destination: "
							+ CLONE_PARENT_DESTINATION.getAbsolutePath());
					CLONE_PARENT_DESTINATION.mkdir();
				} else {
					// System.out.println("Parent Directory exists, leaving it
					// unchanged.");
				}
			} else
				CLONE_PARENT_DESTINATION.mkdir();

			//
			if (CLONE_REPO_DESTINATION.exists() && !replace) {
				workflow.log(CrossflowLogger.SEVERITY.INFO, "Repo already exists and replace is false, not doing anything...");
				return repositoryCloneInst;
			}

			// create local clone destination
			if (!CLONE_REPO_DESTINATION.exists()) {
				CLONE_REPO_DESTINATION.mkdir();
				System.out.println("Successfully created local repository clone destination for specified repository: "
						+ CLONE_REPO_DESTINATION.getAbsolutePath());
			}
			Git git = Git.cloneRepository().setURI(CLONE_SOURCE).setDirectory(CLONE_REPO_DESTINATION).call();
			git.close();
			workflow.log(CrossflowLogger.SEVERITY.INFO, "Successfully cloned specified repo to local clone destination: "
					+ CLONE_REPO_DESTINATION.getAbsolutePath());

		} catch (Exception e) {
			workflow.log(CrossflowLogger.SEVERITY.ERROR, "Error in creating clone:");
			workflow.log(CrossflowLogger.SEVERITY.ERROR, "Repo name chars: ");
			for (char c : CLONE_REPO_DESTINATION.getPath().toCharArray())
				workflow.log(CrossflowLogger.SEVERITY.ERROR, c + " | ");
			workflow.log(CrossflowLogger.SEVERITY.ERROR, "");
			e.printStackTrace();
		}

		return repositoryCloneInst;

	}// consumeRepositorySearchResults

	private String createUniqueFolderForRepo(String name, String url) {

		workflow.log(CrossflowLogger.SEVERITY.INFO, "creating unique hash (SHA-1) for url: " + url);

		String ret = cleanFileName(name);

		// create unique id for the remote url
		MessageDigest md = null;
		try {

			md = MessageDigest.getInstance("SHA-1");

			md.update(url.getBytes());

			ret = ret + "-" + DigestUtils.sha1Hex(md.digest());

		} catch (NoSuchAlgorithmException e) {
			workflow.log(CrossflowLogger.SEVERITY.ERROR, 
					"createUniqueFolderForRepo() tried to create a SHA-1 digest but a NoSuchAlgorithmException was thrown, appending nothing");
		}

		return ret;
	}

	// illegal ascii characters in filenames for various operating systems
	// (mainly windows)
	final static int[] illegalChars = { 34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
			18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47 };
	static {
		Arrays.sort(illegalChars);
	}

	private String cleanFileName(String badFileName) {
		StringBuilder cleanName = new StringBuilder();
		for (int i = 0; i < badFileName.length(); i++) {
			int c = (int) badFileName.charAt(i);
			if (Arrays.binarySearch(illegalChars, c) < 0) {
				cleanName.append((char) c);
			}
		}
		String cleaned = cleanName.toString();
		// remove names ending with one or more "." / " " as this is illegal as
		// well in JGit.
		while (cleaned.endsWith(".") || cleaned.endsWith(" "))
			cleaned = cleaned.substring(0, cleaned.length() - 1);

		return cleaned;
	}

}
