package org.eclipse.scava.crossflow.examples.projectsanalysis;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class JavaRepositoryAnalyzer extends OpinionatedJavaRepositoryAnalyzerBase {

	@Override
	public JavaRepositoryAnalysisResult consumeRepositories(Repository repository) throws Exception {

		JavaRepositoryAnalysisResult javaRepositoryAnalysisResultInst = new JavaRepositoryAnalysisResult();
		javaRepositoryAnalysisResultInst.setPath(repository.getPath());
		javaRepositoryAnalysisResultInst.setUrl(repository.getUrl());

		long sizeAtCommit = 0;
		long numberOfFiles = 0; // offset hidden files?

		// count number of files and total file size (the Java-way; unix-way may be
		// faster: find REPO_DIR -type f | wc -l)
		try (Stream<Path> walk = Files.walk(Paths.get(repository.getPath()))) {
			List<String> repoFileList = walk.filter(Files::isRegularFile).map(x -> x.toString())
					.collect(Collectors.toList());
			numberOfFiles = repoFileList.size();

			for (String fileLoc : repoFileList) {
				FileChannel fileChannel = FileChannel.open(Paths.get(fileLoc));
				sizeAtCommit += fileChannel.size();
			}
			// convert byte to megabyte
			long MEGABYTE = 1024L * 1024L;
			sizeAtCommit = sizeAtCommit / MEGABYTE;
		} // try Stream<Path>

		javaRepositoryAnalysisResultInst.setSizeAtCommit(sizeAtCommit);
		javaRepositoryAnalysisResultInst.setNumberOfFiles(numberOfFiles);

		workflow.log(LogLevel.INFO, "Completed initial Java analysis of " + repository.getPath()
				+ " :: numberOfFiles = " + numberOfFiles + " ; sizeAtCommit = " + sizeAtCommit + "\n");

		return javaRepositoryAnalysisResultInst;

	}// consumeRepositories

	@Override
	public boolean acceptInput(Repository input) {
		// TODO: logic for when to accept tasks for this instance of
		// JavaRepositoryAnalyzer goes here.
		return true;
	}// acceptInput

}
