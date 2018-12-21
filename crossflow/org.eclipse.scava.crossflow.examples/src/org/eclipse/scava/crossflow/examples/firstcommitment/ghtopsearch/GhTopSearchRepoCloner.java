package org.eclipse.scava.crossflow.examples.firstcommitment.ghtopsearch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.scava.crossflow.examples.utils.CloneUtils;

public class GhTopSearchRepoCloner extends GhTopSearchRepoClonerBase {
	
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
	public GhTopSearchRepoCloner() {
		// do nothing
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
	
	public String cloneRepo(String owner, String repo, boolean replace) {
		String ghRepoUrl = "https://github.com/" + owner + "/" + repo;
		final String CLONE_SOURCE = ghRepoUrl + ".git";
		
		final File CLONE_REPO_DESTINATION = new File(CLONE_PARENT_DESTINATION + File.separator
				+ CloneUtils.createUniqueFolderForRepo(ghRepoUrl));
	
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
	
	@Override
	public void consumeGhTopSearchRepos(OwnerRepoTuple ownerRepoTuple) throws Exception {
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getGhTopSearchRepos().send( ownerRepoTuple, this.getClass().getName() );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( ownerRepoTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( ownerRepoTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( ownerRepoTuple.getId() );
				workflow.getGhTopSearchRepos().send( ownerRepoTuple, this.getClass().getName() );
			}
			
			if ( committedRepoMap.containsKey( ownerRepoTuple.getField1() ) ) {
				committedRepoMap.replace( ownerRepoTuple.getField1(), committedRepoMap.get( ownerRepoTuple.getField1()) + 1 );
//				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get( stringStringIntegerTuple.getField1() ) + " occurrences of " + stringStringIntegerTuple.getField1() );
								
				String clonedRepoLocation = cloneRepo(ownerRepoTuple.getField0(), ownerRepoTuple.getField1(), false);
				
				OwnerRepoUrlTuple ownerRepoUrlTuple = new OwnerRepoUrlTuple();
				ownerRepoUrlTuple.setField0(ownerRepoTuple.field0); // github owner
				ownerRepoUrlTuple.setField1(ownerRepoTuple.field1); // github repo
				ownerRepoUrlTuple.setField2(clonedRepoLocation); // cloned repository local path

				sendToGhTopSearchClonedRepoEntries(ownerRepoUrlTuple);

			}
			
		}
		
	}

}