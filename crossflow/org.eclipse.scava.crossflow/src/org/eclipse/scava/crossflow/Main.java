package org.eclipse.scava.crossflow;

/**
 * Main entry point for running Crossflow stub generation externally
 * 
 * @author jonathanco
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// TODO: Use jCommander to parse command line variables
		String metamodelLocation = args[0];
		String projectLocation = args[1];
		String modelRelativePath = args[2];

		GenerateBaseClasses generator = new GenerateBaseClasses(metamodelLocation);
		generator.run(projectLocation, modelRelativePath);
	}
}
