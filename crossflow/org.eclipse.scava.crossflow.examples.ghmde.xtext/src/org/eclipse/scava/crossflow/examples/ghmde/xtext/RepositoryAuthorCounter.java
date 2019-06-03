package org.eclipse.scava.crossflow.examples.ghmde.xtext;


public class RepositoryAuthorCounter extends RepositoryAuthorCounterBase {
	
	@Override
	public Repository consumeRepositoryCloneResults(Repository repository) throws Exception {
		
		Repository repositoryInst = new Repository();
		//	repositoryInst.setUrl( String );
		return repositoryInst;
	
	}


}
