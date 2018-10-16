package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechExample;
import org.eclipse.scava.crossflow.runtime.Mode;

public class GhMdeTechExampleMasterWorkers {
	
	public static void main(String[] args) throws Exception {
		
		GhMdeTechExample master = new GhMdeTechExample();
		master.setName("AtlCounter");
		master.setFavouriteGhMdeTech("atl");
		
		GhMdeTechExample worker1 = new GhMdeTechExample();
		worker1.setName("EolCounter");
		worker1.setMode(Mode.WORKER);
		worker1.setFavouriteGhMdeTech("eol");
		
		GhMdeTechExample worker2 = new GhMdeTechExample();
		worker2.setName("EcoreCounter");
		worker2.setMode(Mode.WORKER);
		worker2.setFavouriteGhMdeTech("ecore");
		
		master.run();
		worker1.run();
		worker2.run();
		
	}
	
}
