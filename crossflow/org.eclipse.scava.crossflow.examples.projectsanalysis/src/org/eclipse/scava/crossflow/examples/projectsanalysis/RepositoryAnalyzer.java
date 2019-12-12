package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class RepositoryAnalyzer extends OpinionatedRepositoryAnalyzerBase {

	@Override
	public RepositoryAnalysisResult consumeRepositoryAnalyses(RepoProjectPair repoProjectPair) throws Exception {

		RepositoryAnalysisResult repositoryAnalysisResultInst = new RepositoryAnalysisResult();
		repositoryAnalysisResultInst.setRepoProject(repoProjectPair);

		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		repositoryBuilder.setMustExist(true);
		repositoryBuilder.setGitDir(new File(repoProjectPair.repo.path + File.separator + ".git"));
		org.eclipse.jgit.lib.Repository gitRepo = repositoryBuilder.findGitDir().build();

		ObjectId oldHead = gitRepo.resolve("HEAD^^^^{tree}");
		ObjectId head = gitRepo.resolve("HEAD^{tree}");

		int linesAdded = 0;
		int linesDeleted = 0;

		// prepare the two iterators to compute the diff between
		try (ObjectReader reader = gitRepo.newObjectReader()) {
			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
			oldTreeIter.reset(reader, oldHead);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);

			// finally get the list of changed files
			try (Git git = new Git(gitRepo)) {
				List<DiffEntry> diffs = git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DiffFormatter df = new DiffFormatter(out);
				df.setRepository(git.getRepository());
				df.setDiffComparator(RawTextComparator.DEFAULT);
				df.setDetectRenames(true);

				for (DiffEntry entry : diffs) {

					for (Edit edit : df.toFileHeader(entry).toEditList()) {
						linesDeleted += edit.getEndA() - edit.getBeginA();
						linesAdded += edit.getEndB() - edit.getBeginB();
					} // Edit loop

				} // DiffEntry loop

			} // Git try
		} // ObbjectReader try

		repositoryAnalysisResultInst.setLines_added(linesAdded);
		repositoryAnalysisResultInst.setLines_deleted(linesDeleted);

		workflow.log(LogLevel.INFO, "Completed analysis of " + repoProjectPair.repo.url + " :: linesAdded = "
				+ linesAdded + " ; linesDeleted = " + linesDeleted + "\n");

		return repositoryAnalysisResultInst;

	}// consumeRepositoryAnalyses

	@Override
	public boolean acceptInput(RepoProjectPair input) {
		// TODO: logic for when to accept tasks for this instance of RepositoryAnalyzer
		// goes here.
		return true;
	}

}
