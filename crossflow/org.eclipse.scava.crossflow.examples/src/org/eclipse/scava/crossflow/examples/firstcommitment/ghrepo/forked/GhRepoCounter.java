package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.crossflow.runtime.utils.Result;

public class GhRepoCounter extends GhRepoCounterBase {

	protected final int MAX_NUMBER_OF_COMMITMENTS = 999999;

	protected Set<String> alreadySeenJobs = new HashSet<String>();

	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>();

	@Override
	public void consumeGhRepos(GhRepo ghRepo) {

		if (committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS) {
			// do not commit to any more repositories - sending back
			workflow.getGhRepos().send(ghRepo,this.getClass().getName());

		} else {
			// We still have space left for repositories to commit to - considering it
			if (alreadySeenJobs.contains(ghRepo.getId())) {
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put(ghRepo.getRepoUrl(), 0);

			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add(ghRepo.getId());
				workflow.getGhRepos().send(ghRepo,this.getClass().getName());
			}

			if (committedRepoMap.containsKey(ghRepo.getRepoUrl())) {
				committedRepoMap.replace(ghRepo.getRepoUrl(), committedRepoMap.get(ghRepo.getRepoUrl()) + 1);
				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get(ghRepo.getRepoUrl())
						+ " occurrences of " + ghRepo.getRepoUrl());
				// send output to sink:
				org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked.Result r = new org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked.Result();
				r.setTechnology("gmf");
				r.setRepos(1);
				r.setFiles(0);
				r.setAuthors(0);
				sendToResultsPublisher(r);
				
				// send output to eclipse:
				Result ret = new Result();
				ret.add("gmf");
				ret.add(1);
				ret.add(0);
				ret.add(0);
				try {
					sendToResultsTopic(ret);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
