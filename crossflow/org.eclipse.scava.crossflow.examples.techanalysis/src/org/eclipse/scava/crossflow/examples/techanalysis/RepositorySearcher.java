package org.eclipse.scava.crossflow.examples.techanalysis;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class RepositorySearcher extends OpinionatedRepositorySearcherBase {

	private class RepositoryClone {

		protected String url;
		protected String name;
		protected String path;

		public void setUrl(String url) {
			this.url = url;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setLocalPath(String clonedRepoLocalPath) {
			this.path = clonedRepoLocalPath;
		}
	}

	//
	
	@Override
	public boolean acceptInput(RepoTechPair input) {
		// TODO logic for accepting repos (if already cloned etc)
		//
		return false;
	}
	
	// do not update a repo if folder already exists -- for development
	final boolean replace = false;

	@Override
	public AnalysisResult consumeRepositorySearches(RepoTechPair repository) throws Exception {

		// System.out.println("reposearcher received:\n\t" + repository.getName() + " |
		// " + repository.getUrl());

		// clone repo if not already present
		RepositoryClone localClone = cloneRepo(repository);

		// use clone to get files (and count)
		AnalysisResult res = countFiles(localClone, repository.tech);

		// use clone to get authors (and count)
		// XXX limit to authors of commits of relevant files
		countAuthors(localClone, res);

		return res;
	}

	private RepositoryClone cloneRepo(Repository repository) {

		RepositoryClone repositoryCloneInst = new RepositoryClone();

		final String CLONE_SOURCE = repository.getUrl() + ".git";

		final File CLONE_PARENT_DESTINATION = TechAnalysisProperties.CLONE_PARENT_DESTINATION;

		final File CLONE_REPO_DESTINATION = new File(CLONE_PARENT_DESTINATION + File.separator
				+ createUniqueFolderForRepo(repository.getName(), repository.getUrl()));

		//
		workflow.log(LogLevel.INFO, "cloning: " + CLONE_SOURCE);
		workflow.log(LogLevel.INFO, "into: " + CLONE_REPO_DESTINATION);
		//

		String clonedRepoLocalPath = CLONE_REPO_DESTINATION.getPath();

		repositoryCloneInst.setUrl(repository.getUrl());
		repositoryCloneInst.setName(repository.getName());
		repositoryCloneInst.setLocalPath(clonedRepoLocalPath);

		try {
			// clean local clone parent destination if it exists
			if (CLONE_PARENT_DESTINATION.exists()) {
				if (replace) {
					FileUtils.deleteDirectory(CLONE_PARENT_DESTINATION);
					workflow.log(LogLevel.INFO, "Successfully cleaned local clone parent destination: "
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
				workflow.log(LogLevel.INFO,
						"Repo already exists and replace is false, not doing anything...");
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
			workflow.log(LogLevel.INFO,
					"Successfully cloned specified repo to local clone destination: "
							+ CLONE_REPO_DESTINATION.getAbsolutePath());

		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, "Error in creating clone:");
			workflow.log(LogLevel.ERROR, "Repo name chars: ");
			for (char c : CLONE_REPO_DESTINATION.getPath().toCharArray())
				workflow.log(LogLevel.ERROR, c + " | ");
			workflow.log(LogLevel.ERROR, "");
			e.printStackTrace();
		}

		return repositoryCloneInst;

	}

	// utility methods for cloning

	private String createUniqueFolderForRepo(String name, String url) {

		workflow.log(LogLevel.INFO, "creating unique hash (SHA-1) for url: " + url);

		String ret = cleanFileName(name);

		// create unique id for the remote url
		MessageDigest md = null;
		try {

			md = MessageDigest.getInstance("SHA-1");

			md.update(url.getBytes());

			ret = ret + "-" + DigestUtils.sha1Hex(md.digest());

		} catch (NoSuchAlgorithmException e) {
			workflow.log(LogLevel.ERROR,
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

	// utility methods for file counting

	private AnalysisResult countFiles(RepositoryClone repositoryClone, Technology tech) {

		workflow.log(LogLevel.INFO, "count ( " + repositoryClone.path + " )");

		File repositoryLocalPath = new File(repositoryClone.path);

		int fileCount = ((List<File>) FileUtils.listFiles(repositoryLocalPath,
				Collections.singleton(tech.fileExt).toArray(new String[1]), true)).size();

		AnalysisResult r = new AnalysisResult();
		r.repository.name = repositoryClone.name;
		r.repository.url = repositoryClone.url;
		r.technology.fileExt = tech.fileExt;
		r.fileCount = fileCount;

		return r;

	}// count

	// utility methods for author count

	private void countAuthors(RepositoryClone repositoryClone, AnalysisResult res) {

		// getOrCreateResult(repositoryClone, ext).fileCount = fileCount;
		// XXX NB: since authors are not linked to technologies yet, assume they all
		// contribute to all technologies found in the files of the repo

		Set<String> repoAuthorsSet = new HashSet<>();

		//

		try {
			FileRepository repo = new FileRepository(new File(repositoryClone.path + "/.git").getCanonicalPath());

			// get a list of all known heads, tags, remotes, ...
			@SuppressWarnings("deprecation")
			Collection<Ref> allRefs = repo.getAllRefs().values();

			// a RevWalk allows to walk over commits based on some filtering that is defined
			try (RevWalk revWalk = new RevWalk(repo)) {
				for (Ref ref : allRefs) {
					revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
				}
				System.out.println("Walking all commits starting with " + allRefs.size() + " refs: " + allRefs);
				for (RevCommit commit : revWalk) {
					repoAuthorsSet.add(commit.getAuthorIdent().getEmailAddress());
				}
			}

		} catch (IOException e) {
			System.err.println("\n" + "[" + workflow.getName() + "] "
					+ "Failed to count repository authors. Wrong cloned repository location?");
			e.printStackTrace();
		}

		//

		res.authorCount = repoAuthorsSet.size();

	}

}
