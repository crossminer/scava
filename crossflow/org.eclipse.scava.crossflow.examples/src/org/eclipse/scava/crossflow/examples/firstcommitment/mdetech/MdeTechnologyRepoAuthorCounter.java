/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class MdeTechnologyRepoAuthorCounter extends MdeTechnologyRepoAuthorCounterBase {

	
	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	/**
	 * 
	 */
	public MdeTechnologyRepoAuthorCounter() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyRepoEntriesConsumer#consumeMdeTechnologyRepoEntries(org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.StringStringStringTuple)
	 */
	@Override
	public void consumeMdeTechnologyClonedRepoEntriesForAuthorCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getMdeTechnologyClonedRepoEntriesForAuthorCounter().send( extensionKeywordStargazersRemoteRepoUrlTuple );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( extensionKeywordStargazersRemoteRepoUrlTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( extensionKeywordStargazersRemoteRepoUrlTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( extensionKeywordStargazersRemoteRepoUrlTuple.getId() );
				workflow.getMdeTechnologyClonedRepoEntriesForAuthorCounter().send( extensionKeywordStargazersRemoteRepoUrlTuple );
			}
			
			if ( committedRepoMap.containsKey( extensionKeywordStargazersRemoteRepoUrlTuple.getField1() ) ) {
				
				committedRepoMap.replace( extensionKeywordStargazersRemoteRepoUrlTuple.getField1(), committedRepoMap.get( extensionKeywordStargazersRemoteRepoUrlTuple.getField1()) + 1 );
				
				int authorCount = count(extensionKeywordStargazersRemoteRepoUrlTuple.getField1());
				
				ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple = new ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple();
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField0(extensionKeywordStargazersRemoteRepoUrlTuple.field0); // file extension
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField1(extensionKeywordStargazersRemoteRepoUrlTuple.field1); // repository remote URL
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField2(extensionKeywordStargazersRemoteRepoUrlTuple.field2); // repository number of stars
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField3(extensionKeywordStargazersRemoteRepoUrlTuple.field3); // cloned repository local path
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField4(authorCount); // repository unique author count
				
				getMdeTechnologyRepoAuthorCountEntries().send(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
			
			}
			
		}
		
	}

	private int count(String repoLocation) {	
		HashSet<String> repoAuthorsSet = new HashSet<String>();
		
		try {
			Repository repo = new FileRepository(new File(repoLocation).getCanonicalPath());
			
			// get a list of all known heads, tags, remotes, ...
            Collection<Ref> allRefs = repo.getAllRefs().values();

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk revWalk = new RevWalk( repo )) {
                for( Ref ref : allRefs ) {
                    revWalk.markStart( revWalk.parseCommit( ref.getObjectId() ));
                }
//                System.out.println("Walking all commits starting with " + allRefs.size() + " refs: " + allRefs);
                for( RevCommit commit : revWalk ) {
                	repoAuthorsSet.add(commit.getAuthorIdent().getEmailAddress());
                }
            }
		
		} catch (IOException e) {
			System.err.println("\n" + "[" + workflow.getName() + "] " + "Failed to count repository authors. Wrong cloned repository location?");
			e.printStackTrace();
		}	 
		
		return repoAuthorsSet.size();
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
		MdeTechnologyRepoAuthorCounter counter = new MdeTechnologyRepoAuthorCounter();
		String repoLocation = "../../.git";
		System.out.println(new File(repoLocation).getCanonicalPath());
		int count = counter.count(repoLocation);
		System.out.println("COUNT: " + count);
	}

}
