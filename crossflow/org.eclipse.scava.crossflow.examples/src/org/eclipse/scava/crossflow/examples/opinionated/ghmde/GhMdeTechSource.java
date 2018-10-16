package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTech;
import org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechSourceBase;

public class GhMdeTechSource extends GhMdeTechSourceBase {
	
	protected List<String> ghMdeTechs;
	
	public GhMdeTechSource() {
		// TODO: replace with actual search results obtained from GitHub
		
		ghMdeTechs = new ArrayList<String>();
		for (int i=0;i<10;i++) {
			ghMdeTechs.add("atl");
			ghMdeTechs.add("eol");
			ghMdeTechs.add("ecore");
		}
	}
	
	@Override
	public void produce() {
		for (String fileExtension : ghMdeTechs) {
			GhMdeTech ghMdeTech = new GhMdeTech();
			ghMdeTech.setFileExtension(fileExtension);
			getGhMdeTechs().send(ghMdeTech);
		}
	}
}
