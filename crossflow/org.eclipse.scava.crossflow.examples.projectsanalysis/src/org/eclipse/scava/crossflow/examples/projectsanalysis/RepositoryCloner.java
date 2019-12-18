package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class RepositoryCloner extends RepositoryClonerBase {

	public static String CLONE_PATH_PREFIX = "/tmp/REPO-CLONES";

	@Override
	public Repository consumeProjects(Project project) throws Exception {

		Repository repositoryInst = new Repository();

		String repoUrl = "https://github.com/" + project.owner + "/" + project.repo + ".git";
		File pathToRepoClone = new File(CLONE_PATH_PREFIX + File.separator + project.owner + File.separator
				+ project.repo + File.separator + project.commit + File.separator);
		pathToRepoClone.mkdirs(); // make directory if it doesn't yet exist

		try {
			// clone repository
			workflow.log(LogLevel.INFO, "Cloning " + repoUrl + " ...");
			if ( !pathToRepoClone.exists() ) {
				Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(pathToRepoClone).call();
				workflow.log(LogLevel.INFO, "SUCCESSFULLY cloned " + repoUrl + " !\n");
	
				if ( project.commit != null && !project.commit.isEmpty() ) {
					// checkout specific commit by its ID (see: http://bit.ly/2LRlr5g)
					workflow.log(LogLevel.INFO, "Checking out commit " + project.commit + " ...");
					git.checkout().setName(project.commit).call();
					workflow.log(LogLevel.INFO, "SUCCESSFULLY checked out commit " + project.commit + " !\n");
				}
	
				git.close();
			} else {
				workflow.log(LogLevel.INFO, "Repository clone already exists for " + repoUrl + " !\n");
			}

			repositoryInst.setPath(pathToRepoClone.getAbsolutePath());
			repositoryInst.setUrl(repoUrl);

		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, "FAILED! Error in cloning repository from: " + repoUrl + " ... ");
			workflow.log(LogLevel.ERROR, "... to clone location: " + pathToRepoClone + "\n");
			e.printStackTrace();
		}

		return repositoryInst;

	}// consumeProjects

}// RepositoryCloner
