package org.eclipse.scava.crossflow.examples.opinionated;

import org.eclipse.scava.crossflow.runtime.Mode;

public class OpinionatedExampleMasterWorkers {
	
	public static void main(String[] args) throws Exception {
		
		OpinionatedExample master = new OpinionatedExample();
		master.setName("ApplesCounter");
		master.setFavouriteWord("apple");
		
		OpinionatedExample worker1 = new OpinionatedExample(Mode.WORKER);
		worker1.setName("BananasCounter");
		worker1.setFavouriteWord("banana");
		
		OpinionatedExample worker2 = new OpinionatedExample(Mode.WORKER);
		worker2.setName("OrangesCounter");
		worker2.setFavouriteWord("orange");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
