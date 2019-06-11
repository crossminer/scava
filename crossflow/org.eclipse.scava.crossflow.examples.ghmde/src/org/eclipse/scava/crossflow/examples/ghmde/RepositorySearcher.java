package org.eclipse.scava.crossflow.examples.ghmde;


public class RepositorySearcher extends CommitmentRepositorySearcherBase {
	
	@Override
	public AnalysisResult consumeRepositorySearches(Repository repository) throws Exception {
		
		AnalysisResult analysisResultInst = new AnalysisResult();
		//	analysisResultInst.setFileCount( int );
		//	analysisResultInst.setAuthorCount( int );
		//	analysisResultInst.setRepository( String );
		//	analysisResultInst.setTechnology( String );
		//	analysisResultInst.setName( String );
		return analysisResultInst;
	
	}
	@Override
	public void consumeMDETechnologyCollectionTopic(MDETechnologyCollection mDETechnologyCollection) throws Exception {
		
		// TODO: handle configuration		
	
	}


}
