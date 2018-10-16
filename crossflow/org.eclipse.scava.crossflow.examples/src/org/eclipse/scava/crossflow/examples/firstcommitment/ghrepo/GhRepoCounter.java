package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.GhRepo;
import org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.GhRepoCounterBase;

public class GhRepoCounter extends GhRepoCounterBase {

	protected final int MAX_NUMBER_OF_COMMITMENTS = 128;

	protected Set<String> alreadySeenJobs = new HashSet<String>();

	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>();

	@Override
	public void consumeGhRepos(GhRepo ghRepo) {

		if (committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS) {
			// do not commit to any more repositories - sending back
			workflow.getGhRepos().send(ghRepo);

		} else {
			// We still have space left for repositories to commit to - considering it
			if (alreadySeenJobs.contains(ghRepo.getId())) {
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put(ghRepo.getRepoUrl(), 0);

			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add(ghRepo.getId());
				workflow.getGhRepos().send(ghRepo);
			}

			if (committedRepoMap.containsKey(ghRepo.getRepoUrl())) {
				committedRepoMap.replace(ghRepo.getRepoUrl(), committedRepoMap.get(ghRepo.getRepoUrl()) + 1);
				System.out.println("[" + workflow.getName() + "] " + committedRepoMap.get(ghRepo.getRepoUrl())
						+ " occurrences of " + ghRepo.getRepoUrl());
				// send output to eclipse:
				Object[] r = new Object[4];
				r[0] = "gmf";
				r[1] = 1;
				r[2] = 0;
				r[3] = 0;
				getResultsPublisher().send(r);
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
