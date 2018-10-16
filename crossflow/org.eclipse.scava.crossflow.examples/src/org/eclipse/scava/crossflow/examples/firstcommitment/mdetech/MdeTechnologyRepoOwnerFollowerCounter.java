/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author blizzfire
 *
 */
public class MdeTechnologyRepoOwnerFollowerCounter extends MdeTechnologyRepoOwnerFollowerCounterBase {

	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	/**
	 * 
	 */
	public MdeTechnologyRepoOwnerFollowerCounter() {
		// do nothing
	}

	@Override
	public void consumeMdeTechnologyRepoEntries(StringStringIntegerTuple stringStringIntegerTuple) {
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getMdeTechnologyRepoEntries().send( stringStringIntegerTuple );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( stringStringIntegerTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( stringStringIntegerTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( stringStringIntegerTuple.getId() );
				workflow.getMdeTechnologyRepoEntries().send( stringStringIntegerTuple );
			}
			
			if ( committedRepoMap.containsKey( stringStringIntegerTuple.getField1() ) ) {
				committedRepoMap.replace( stringStringIntegerTuple.getField1(), committedRepoMap.get( stringStringIntegerTuple.getField1()) + 1 );
				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get( stringStringIntegerTuple.getField1() ) + " occurrences of " + stringStringIntegerTuple.getField1() );
				
				System.out.println("[" + workflow.getName() + "] " + "ISSUE NEW GITHUB API CALL HERE !\n");

				// TODO: issue new Github API call here !
			
			}
			
		}
		
	}

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
