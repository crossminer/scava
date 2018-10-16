package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class GitRepoCloner {
	
	final static File CLONE_PARENT_DESTINATION = new File(
					// level: same as this (crossflow) repo
					".." + File.separator + ".." + File.separator + File.separator + "CLONED-REPOS");

	public static void main(String[] args) throws IOException {
		File file = new File(".." + File.separator + ".." + File.separator + File.separator + "CLONED-REPOS");
		System.out.println(file.getCanonicalPath().toString());
	}
	
	public static String cloneRepo(String repoUrl, boolean replace) {
			final String CLONE_SOURCE = repoUrl + ".git";

			
			final File CLONE_REPO_DESTINATION = new File(CLONE_PARENT_DESTINATION + File.separator
					+ CloneUtils.createUniqueFolderForRepo(repoUrl));

			System.out.println("Aiming to clone Git repository " + CLONE_SOURCE + " ... ");
			System.out.println("... to " + CLONE_REPO_DESTINATION);

			String ret = CLONE_REPO_DESTINATION.getPath();

			try {
				// create local clone parent destination if it does not exists
				if ( !CLONE_PARENT_DESTINATION.exists() ) {
					CLONE_PARENT_DESTINATION.mkdir();
				}
				//
				if (CLONE_REPO_DESTINATION.exists() && !replace) {
					System.out.println("Repo already exists and replace is false, not doing anything...");
					return ret;
				}

				// create local clone destination
				if (!CLONE_REPO_DESTINATION.exists()) {
					CLONE_REPO_DESTINATION.mkdir();
					System.out.println("Successfully created local repository clone destination for specified repository: "
							+ CLONE_REPO_DESTINATION.getCanonicalPath());
				}
				Git git = Git.cloneRepository().setURI(CLONE_SOURCE).setDirectory(CLONE_REPO_DESTINATION).call();
				git.close();
				System.out.println("Successfully cloned specified repo to local clone destination: "
						+ CLONE_REPO_DESTINATION.getAbsolutePath());

			} catch (Exception e) {
				System.err.println("Error in creating clone:");
				System.err.print("Repo name chars: ");
				for (char c : CLONE_REPO_DESTINATION.getPath().toCharArray())
					System.err.print(c + " | ");
				System.err.println();
				e.printStackTrace();
			}

			return ret;
		}
	
	public static void cleanLocalParentCloneDirectory() {
		// clean local clone parent destination if it exists
		if (CLONE_PARENT_DESTINATION.exists()) {
			try {
				FileUtils.deleteDirectory(CLONE_PARENT_DESTINATION);
				System.out.println("Successfully cleaned local clone parent destination: "
						+ CLONE_PARENT_DESTINATION.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CLONE_PARENT_DESTINATION.mkdir();
		}
	}
	
}
	
	

