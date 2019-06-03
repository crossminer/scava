package org.eclipse.scava.crossflow.examples.ghmde;


public class RepositoryCloner extends RepositoryClonerBase {
	
	@Override
	public RepositoryClone consumeRepositorySearchResults(Repository repository) throws Exception {
		
		RepositoryClone repositoryCloneInst = new RepositoryClone();
		//	repositoryCloneInst.setLocalPath( String );
		return repositoryCloneInst;
	
	}


}
