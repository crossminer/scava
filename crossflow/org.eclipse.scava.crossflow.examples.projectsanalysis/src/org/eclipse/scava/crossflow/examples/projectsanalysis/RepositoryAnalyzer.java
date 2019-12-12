package org.eclipse.scava.crossflow.examples.projectsanalysis;


public class RepositoryAnalyzer extends OpinionatedRepositoryAnalyzerBase {
	
	@Override
	public RepositoryAnalysisResult consumeRepositoryAnalyses(RepoProjectPair repoProjectPair) throws Exception {
		
		RepositoryAnalysisResult repositoryAnalysisResultInst = new RepositoryAnalysisResult();
		//	repositoryAnalysisResultInst.setSize_at_commit( String );
		//	repositoryAnalysisResultInst.setLines_added( String );
		//	repositoryAnalysisResultInst.setLines_deleted( String );
		return repositoryAnalysisResultInst;
	
	}

	@Override
	public boolean acceptInput(RepoProjectPair input) {
		// TODO: logic for when to accept tasks for this instance of RepositoryAnalyzer goes here.
		return true;
	}

}
