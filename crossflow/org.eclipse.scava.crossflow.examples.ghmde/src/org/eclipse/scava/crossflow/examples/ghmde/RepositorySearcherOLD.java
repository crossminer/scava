/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.query.CodeSearchQuery;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;

public class RepositorySearcherOLD extends RepositorySearcherBase {
	
protected final int MAX_NUMBER_OF_COMMITMENTS = 999999;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < file extension "+" keyword (combination) >
	protected List<String> committedTupleCombinationList = new LinkedList<String>(); 
	
	@Override
	public void consumeMDETechnologyTuples(MDETechnologyTuple tuple) throws Exception {
		
		if ( committedTupleCombinationList.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more MDE technology tuples - sending back
			workflow.getMDETechnologyTuples().send( tuple ,this.getClass().getName());
		
		} else {
			// We still have resources left for MDE technology tuples to commit to - considering it
			if ( alreadySeenJobs.contains( tuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedTupleCombinationList.add( tuple.getFileExt() + "+" + tuple.getTechKey() );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( tuple.getId() );
				workflow.getMDETechnologyTuples().send( tuple ,this.getClass().getName());
			}
			
			if ( committedTupleCombinationList.contains( tuple.getFileExt() + "+" + tuple.getTechKey() ) ) {
				// We have seen this job before and committed to handle it
				search( tuple );
			}
			
		}
		
	}// consumeMDETechnologyTuples

	private void search(MDETechnologyTuple tuple) {
		Repository repositoryInst = new Repository();

		String query = new CodeSearchQuery().create(tuple.getTechKey()).extension(tuple.getFileExt()).inFile().build().getQuery();
		
		IDataSet<SearchCode> ret = GitHubUtils.getOAuthClient().getSearchCode("asc", query, null);
		
		List<SearchCode> repoFiles = ret.observe().toList().blockingGet();

		// files in current repo
		for (SearchCode resultItem : repoFiles) {
			org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode.Repository resultRepo = resultItem.getRepository();
			
			repositoryInst.setFileExt( tuple.getFileExt() );
			repositoryInst.setTechKey( tuple.getTechKey() );
			repositoryInst.setUrl( resultRepo.getHtmlUrl() );
			repositoryInst.setName( resultRepo.getFullName() );
			
			sendToRepositorySearchResults( repositoryInst);					
		}
		
	}// search
	
}// RepositorySearcher
