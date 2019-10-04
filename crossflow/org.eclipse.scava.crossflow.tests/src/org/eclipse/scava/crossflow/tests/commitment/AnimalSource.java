package org.eclipse.scava.crossflow.tests.commitment;

import java.util.ArrayList;
import java.util.List;

public class AnimalSource extends AnimalSourceBase {
	
	protected List<String> animalNames = new ArrayList<>();
	
	@Override
	public void produce() {
		for (String name : animalNames) {
			sendToAnimals(new Animal(name));
		}
	}

	public void setAnimalNames(List<String> animalNames) {
		this.animalNames = animalNames;
	}
}