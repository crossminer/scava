package org.eclipse.scava.crossflow.examples.projectsanalysis;


public class RepositoryAnalysisDispatcher extends RepositoryAnalysisDispatcherBase {
	
	@Override
	public void consumeRepositories(Repository repository) throws Exception {
		
		// TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		RepoProjectPair repoProjectPair1 = new RepoProjectPair();
		//	repoProjectPair1.setPath( String );
		sendToRepositoryAnalyses( repoProjectPair1);		
	
	}


}
