package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.Arrays;
import java.util.List;

public class TechnologySource extends TechnologySourceBase {
	
	protected List<Technology> technologies;
	
	@Override
	public void produce() throws Exception {
		
		technologies = Arrays.asList(new Technology("eugenia", "gmf.node", "ecore"), new Technology("eol", "var", "eol"));
		
		for (Technology technology : technologies) {
			sendToTechnologies(technology);
		}
		
	}
	
	public List<Technology> getTechnologies() {
		return technologies;
	}

}