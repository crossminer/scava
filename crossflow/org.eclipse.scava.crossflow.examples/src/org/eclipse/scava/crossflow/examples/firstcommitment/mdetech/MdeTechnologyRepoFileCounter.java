package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class MdeTechnologyRepoFileCounter extends MdeTechnologyRepoFileCounterBase {

	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	

	final static File CLONE_PARENT_DESTINATION = new File(
					// level: same as this (scava) repo
					".." + File.separator + ".." + File.separator + ".." + File.separator + File.separator + "CLONED-REPOS");
	
	public MdeTechnologyRepoFileCounter() {
		// do nothing
	}

	@Override
	public void consumeMdeTechnologyClonedRepoEntries(StringStringIntegerStringTuple stringStringIntegerStringTuple) {
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getMdeTechnologyClonedRepoEntries().send( stringStringIntegerStringTuple );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( stringStringIntegerStringTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( stringStringIntegerStringTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( stringStringIntegerStringTuple.getId() );
				workflow.getMdeTechnologyClonedRepoEntries().send( stringStringIntegerStringTuple );
			}
			
			if ( committedRepoMap.containsKey( stringStringIntegerStringTuple.getField1() ) ) {
				committedRepoMap.replace( stringStringIntegerStringTuple.getField1(), committedRepoMap.get( stringStringIntegerStringTuple.getField1()) + 1 );
				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get( stringStringIntegerStringTuple.getField1() ) + " occurrences of " + stringStringIntegerStringTuple.getField1() );
				
				System.out.println("[" + workflow.getName() + "] " + "COUNT FILES FROM LOCALLY CLONED REPO HERE !\n");
				// TODO: count files from locally cloned repo here !
			
			}
			
		}
		
	}

	private int count(String repoLocation) {	
		// TODO: count files in cloned repo that match technology file extension specified in MDE.java
		return 0;
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
	
	public static void main(String args[]) throws IOException {
		MdeTechnologyRepoFileCounter counter = new MdeTechnologyRepoFileCounter();
		String repoLocation = "../../.git";
		System.out.println(new File(repoLocation).getCanonicalPath());
		int count = counter.count(repoLocation);
		System.out.println("COUNT: " + count);
	}
}
