package org.eclipse.scava.crossflow.examples.ghmde.xtext;


public class RepositoryCloner extends RepositoryClonerBase {
	
	@Override
	public Repository consumeRepositorySearchResults(Repository repository) throws Exception {
		
		Repository repositoryInst = new Repository();
		//	repositoryInst.setUrl( String );
		return repositoryInst;
	
	}


}
