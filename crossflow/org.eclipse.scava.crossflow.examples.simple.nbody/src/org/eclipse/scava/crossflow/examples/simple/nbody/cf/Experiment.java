package org.eclipse.scava.crossflow.examples.simple.nbody.cf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Read a list of experiments to run from the <code>experiments.csv</code> file in the input
 * directory.
 * <p>
 * Each line of the file is a comma separated list of the following values:
 * <ol>
 * 	<li> <b>size (int)</b>: 		total number of bodies
 *  <li> <b>steps (int)</b>: 		number of steps to run
 *  <li> <b>dmp (double)</b>: 		dampening factor for the particles velocities
 *  <li> <b>timeDelta (double)</b>:	the time delta of each simulation step
 *  <li> <b>cuboids (int)</b>:		the number of cuboids in which the simulation cube is divided
 *  <li> <b>threaded (bool)</b>:	if simulation jobs should use threads
 * </ol>
 * @author Horacio Hoyos Rodriguez
 *
 */
public class Experiment extends ExperimentBase {
	
	@Override
	public void produce() throws Exception {
		File file = new File(workflow.getInputDirectory(), "experiments.csv").getAbsoluteFile();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println("Experiment line " + line);
			sendToLines(new Line(line));
		}
		reader.close();
	}

}
