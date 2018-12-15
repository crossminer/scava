package org.eclipse.scava.crossflow.tests.commitment;

import java.util.HashSet;
import java.util.Set;

public class AnimalCounter extends AnimalCounterBase {
	
	protected int executions = 0;
	protected int rejections = 0;
	protected int occurences = 0;
	
	protected Set<String> seen = new HashSet<>();
	protected Set<String> committments = new HashSet<>();
	
	@Override
	public void consumeAnimals(Animal animal) {
		
		executions ++;
		
		if (committments.contains(animal.getName())) {
			occurences ++;
		}
		else if (seen.contains(animal.getId())) {
			committments.add(animal.getName());
			occurences++;
		}
		else {
			seen.add(animal.getId());
			rejections++;
			sendToAnimals(animal);
		}
	}

	public int getExecutions() {
		return executions;
	}
	
	public int getRejections() {
		return rejections;
	}
	
	public int getOccurences() {
		return occurences;
	}
	
}