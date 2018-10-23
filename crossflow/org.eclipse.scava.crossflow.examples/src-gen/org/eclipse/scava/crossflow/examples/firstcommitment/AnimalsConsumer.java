package org.eclipse.scava.crossflow.examples.firstcommitment;

public interface AnimalsConsumer {

	public void consumeAnimals(Animal animal);
	
	/**
	 * wraps consumeAnimals() to provide task status information
	 */
	public void consumeAnimalsActual(Animal animal);

}