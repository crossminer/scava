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

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.scava.crossflow.runtime.utils.CrossflowLogger;

public class RepositoryFileCounter extends RepositoryFileCounterBase {
	
	protected static final MDETechnologyTupleSource mdeTechnologyTupleSource = new MDETechnologyTupleSource();
	
	protected final int MAX_NUMBER_OF_COMMITMENTS = 999999;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	
	@Override
	public RepositoryResult consumeRepositoryCloneResults(RepositoryClone repositoryClone) throws Exception {
		
		RepositoryResult repositoryResultInst = new RepositoryResult();
		
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getRepositoryCloneResults().send( repositoryClone ,this.getClass().getName());
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( repositoryClone.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( repositoryClone.getUrl(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( repositoryClone.getId() );
				workflow.getRepositoryCloneResults().send( repositoryClone ,this.getClass().getName());
			}
			
			if ( committedRepoMap.containsKey( repositoryClone.getUrl() ) ) {
				// We have seen this job before and committed to handle it
				committedRepoMap.replace( repositoryClone.getUrl(), committedRepoMap.get( repositoryClone.getUrl()) + 1 );
				int fileCount = count( repositoryClone );
				repositoryResultInst.setFileCount( fileCount );
			}
			
		}
		
		return repositoryResultInst;

	}// consumeRepositoryCloneResults
	
	private int count(RepositoryClone repositoryClone) {	
		workflow.log(CrossflowLogger.SEVERITY.INFO, "count ( " + repositoryClone.getLocalPath() + " )");
		
		File repositoryLocalPath = new File(repositoryClone.getLocalPath());
		String[] fileExtensions = new String[] {repositoryClone.getFileExt()};
		
		List<File> files = (List<File>) FileUtils.listFiles(repositoryLocalPath, fileExtensions, true);
		
		return files.size();
	}// count

	/**
	 * @return the alreadySeenJobs
	 */
	public Set<String> getAlreadySeenJobs() {
		return alreadySeenJobs;
	}

	/**
	 * @return the committedRepoMap
	 */
	public Map<String, Integer> getCommittedRepoMap() {
		return committedRepoMap;
	}
	
}
