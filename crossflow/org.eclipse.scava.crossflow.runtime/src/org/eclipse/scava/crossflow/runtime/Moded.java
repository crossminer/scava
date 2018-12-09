package org.eclipse.scava.crossflow.runtime;

import com.beust.jcommander.Parameter;

public class Moded {
	
	@Parameter(names = {
	"-mode" }, description = "Must be master_bare, master or worker", converter = ModeConverter.class)
	protected Mode mode = Mode.MASTER;
	
	public Mode getMode() {
		return mode;
	}
}
