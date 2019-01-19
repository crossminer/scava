package org.eclipse.scava.crossflow.examples.github.topsearch;

import java.io.File;

public class GhTopSearchRepoProperties {

	final static File CLONE_PARENT_DESTINATION = new File(
	// level: same as this repo (scava)
	".." + File.separator + ".." + File.separator + ".." + File.separator + "CLONED-REPOS");
	
	protected final static int MAX_NUMBER_OF_COMMITMENTS = 128;

}
