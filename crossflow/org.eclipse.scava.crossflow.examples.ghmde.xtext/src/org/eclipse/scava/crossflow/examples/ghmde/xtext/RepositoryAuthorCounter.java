/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.ghmde.xtext;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class RepositoryAuthorCounter extends RepositoryAuthorCounterBase {
	
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
				int authorCount = count( repositoryClone.getLocalPath() );
				repositoryResultInst.setAuthorCount( authorCount );
			}
			
		}
		
		return repositoryResultInst;
	
	}// consumeRepositoryCloneResults
	
	private int count(String repoLocation) {	
		System.out.println("count ( " + repoLocation + " )");
		HashSet<String> repoAuthorsSet = new HashSet<String>();
		
		try {
			FileRepository repo = new FileRepository(new File(repoLocation + "/.git").getCanonicalPath());
			
			// get a list of all known heads, tags, remotes, ...
            @SuppressWarnings("deprecation")
			Collection<Ref> allRefs = repo.getAllRefs().values();

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk revWalk = new RevWalk( repo )) {
                for( Ref ref : allRefs ) {
                    revWalk.markStart( revWalk.parseCommit( ref.getObjectId() ));
                }
                System.out.println("Walking all commits starting with " + allRefs.size() + " refs: " + allRefs);
                for( RevCommit commit : revWalk ) {
                	repoAuthorsSet.add(commit.getAuthorIdent().getEmailAddress());
                }
            }
		
		} catch (IOException e) {
			System.err.println("\n" + "[" + workflow.getName() + "] " + "Failed to count repository authors. Wrong cloned repository location?");
			e.printStackTrace();
		}	 
		
		return repoAuthorsSet.size();
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
