package org.eclipse.scava.crossflow.examples.ghmde.xtext;


public class RepositoryFileCounter extends RepositoryFileCounterBase {
	
	@Override
	public Repository consumeRepositoryCloneResults(Repository repository) throws Exception {
		
		Repository repositoryInst = new Repository();
		//	repositoryInst.setUrl( String );
		return repositoryInst;
	
	}


}
