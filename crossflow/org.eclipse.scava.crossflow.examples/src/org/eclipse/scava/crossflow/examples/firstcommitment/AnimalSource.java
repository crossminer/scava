package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalSource extends AnimalSourceBase {
	
	List<String> animals = Arrays.asList("dog", "cat", "horse");
	
	@Override
	public void produce() {
		
		final Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			protected int runs = 0;
			
			@Override
			public void run() {
				Animal animal = new Animal();
				animal.setName(animals.get(ThreadLocalRandom.current().nextInt(animals.size())));
				getAnimals().send(animal);
				runs ++;
				if (runs >= 10) timer.cancel();
			}
		}, 0, 200);
	}
}
