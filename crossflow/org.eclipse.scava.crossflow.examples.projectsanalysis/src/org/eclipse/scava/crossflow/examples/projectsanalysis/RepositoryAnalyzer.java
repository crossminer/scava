package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
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

		long sizeAtCommit = 0;
		long numberOfFiles = 0; // offset hidden files?
		int numberOfCommits = 0;
		int linesAdded = 0;
		int linesDeleted = 0;

		// count number of files in repository (the Java-way; unix-way may be faster: find REPO_DIR -type f | wc -l)
		try (Stream<Path> walk = Files.walk(Paths.get(repoProjectPair.repo.path))) {
			List<String> repoFileList = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
			numberOfFiles = repoFileList.size();
		}
		
		// count number of commits in repository 
        try (RevWalk walk = new RevWalk(gitRepo)) {
        	Ref headRef = gitRepo.exactRef("HEAD");
            RevCommit commit = walk.parseCommit(headRef.getObjectId());
            walk.markStart(commit);
            for (RevCommit rev : walk) {
            	numberOfCommits++;
            }
            walk.dispose();
        }// try RevWalk

		// prepare the two iterators to compute the diff between
		try (ObjectReader reader = gitRepo.newObjectReader()) {
			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
			oldTreeIter.reset(reader, oldHead);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);
			sizeAtCommit = reader.getObjectSize(head, ObjectReader.OBJ_ANY);

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

			} // try Git
		} // try ObbjectReader

		repositoryAnalysisResultInst.setLines_added(linesAdded);
		repositoryAnalysisResultInst.setLines_deleted(linesDeleted);

		workflow.log(LogLevel.INFO,
				"Completed analysis of " + repoProjectPair.repo.url + " :: numberOfCommits = " + numberOfCommits
						+ " ; numberOfFiles = " + numberOfFiles + " ; sizeAtCommit = " + sizeAtCommit
						+ " ; linesAdded = " + linesAdded + " ; linesDeleted = " + linesDeleted + "\n");

		return repositoryAnalysisResultInst;

	}// consumeRepositoryAnalyses

	@Override
	public boolean acceptInput(RepoProjectPair input) {
		// TODO: logic for when to accept tasks for this instance of RepositoryAnalyzer
		// goes here.
		return true;
	}

}
