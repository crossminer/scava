/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;

/**
 * @author blizzfire
 *
 */
public class MdeTechnologyRepoCloner extends MdeTechnologyRepoClonerBase {

	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this repo (scava)
			".." + File.separator + ".." + File.separator + ".." + File.separator + "CLONED-REPOS");
	
	
	/**
	 * 
	 */
	public MdeTechnologyRepoCloner() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyRepoEntriesConsumer#consumeMdeTechnologyRepoEntries(org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.StringStringIntegerTuple)
	 */
	@Override
	public void consumeMdeTechnologyRepoEntries(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple) {
		
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getMdeTechnologyRepoEntries().send( extensionKeywordStargazersTuple );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( extensionKeywordStargazersTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( extensionKeywordStargazersTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( extensionKeywordStargazersTuple.getId() );
				workflow.getMdeTechnologyRepoEntries().send( extensionKeywordStargazersTuple );
			}
			
			if ( committedRepoMap.containsKey( extensionKeywordStargazersTuple.getField1() ) ) {
				committedRepoMap.replace( extensionKeywordStargazersTuple.getField1(), committedRepoMap.get( extensionKeywordStargazersTuple.getField1()) + 1 );
//				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get( stringStringIntegerTuple.getField1() ) + " occurrences of " + stringStringIntegerTuple.getField1() );
								
				String clonedRepoLocation = cloneRepo(extensionKeywordStargazersTuple.getField1(), false);
				
				ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple = new ExtensionKeywordStargazersRemoteRepoUrlTuple();
				extensionKeywordStargazersRemoteRepoUrlTuple.setField0(extensionKeywordStargazersTuple.field0); // file extension
				extensionKeywordStargazersRemoteRepoUrlTuple.setField1(extensionKeywordStargazersTuple.field1); // repository remote URL
				extensionKeywordStargazersRemoteRepoUrlTuple.setField2(extensionKeywordStargazersTuple.field2); // repository number of stars
				extensionKeywordStargazersRemoteRepoUrlTuple.setField3(clonedRepoLocation); // cloned repository local path

				getMdeTechnologyClonedRepoEntriesForAuthorCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple);
				getMdeTechnologyClonedRepoEntriesForFileCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple);
				getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple);

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
	
	public String cloneRepo(String repoUrl, boolean replace) {
		final String CLONE_SOURCE = repoUrl + ".git";
		
		final File CLONE_REPO_DESTINATION = new File(CLONE_PARENT_DESTINATION + File.separator
				+ CloneUtils.createUniqueFolderForRepo(repoUrl));
	
		System.out.print("\n" + "[" + workflow.getName() + "] " + "Cloning Git repository " + CLONE_SOURCE + " to " + CLONE_REPO_DESTINATION + " ... ");
	
		String ret = CLONE_REPO_DESTINATION.getAbsolutePath();
	
		try {
			// create local clone parent destination if it does not exists
			if ( !CLONE_PARENT_DESTINATION.exists() ) {
				CLONE_PARENT_DESTINATION.mkdir();
			}
			
			if ( CLONE_REPO_DESTINATION.exists() && !replace ) {
				System.out.print("SKIPPED (repo pre-exists and replace is false) !");
				return ret;
				
			} else if ( !CLONE_REPO_DESTINATION.exists() || replace ) {
				// create local clone destination if it does not yet exist
				if ( !CLONE_REPO_DESTINATION.exists() ) {
					CLONE_REPO_DESTINATION.mkdir();
				}							
				
				Git git = Git.cloneRepository().setURI(CLONE_SOURCE).setDirectory(CLONE_REPO_DESTINATION.getAbsoluteFile()).call();
				git.close();
				System.out.println("COMPLETED !");
				
			}				
	
		} catch (JGitInternalException e) {
			if (e.getMessage().contains("Could not rename file")) {
				// JGit bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=481187
				// fail silently
			}
			
		} catch (Exception e) {
			System.out.println("FAILED !");
			System.err.println("\n" + "[" + workflow.getName() + "] " + "Error in creating clone: " + e.getMessage());
		}
	
		return ret;
	}
	

	public static void main(String[] args) throws IOException {
		MdeTechnologyRepoCloner cloner = new MdeTechnologyRepoCloner();
		MdeTechnologyExample testWorkflow = new MdeTechnologyExample();
		testWorkflow.setName("test");
		cloner.setWorkflow(testWorkflow);
		System.out.println(CLONE_PARENT_DESTINATION.getCanonicalPath().toString());
		cloner.cloneRepo("https://github.com/epsilonlabs/epsilon-atom", true);
	}

}
