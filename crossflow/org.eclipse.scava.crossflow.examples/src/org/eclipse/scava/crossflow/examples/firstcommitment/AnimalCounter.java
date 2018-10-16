package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.HashSet;
import java.util.Set;

public class AnimalCounter extends AnimalCounterBase {
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	protected String favouriteAnimal;
	protected int occurences = 0;
	
	@Override
	public void consumeAnimals(Animal animal) {
		
		if (favouriteAnimal == null) {
			if (alreadySeenJobs.contains(animal.getId())) { 
				// We've seen this job before - assume no-one else wants it
				favouriteAnimal = animal.getName();
			}
			else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add(animal.getId());
				workflow.getAnimals().send(animal);
			}
		}
		
		if (animal.getName().equals(favouriteAnimal)) {
			occurences ++;
			System.out.println("[" + workflow.getName() + "] " + occurences + " occurences of " + favouriteAnimal);
		}
		else if (favouriteAnimal != null){
			// Not our favourite animal so sending back
			workflow.getAnimals().send(animal);
		}
		
		
	}
}
