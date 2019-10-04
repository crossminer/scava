package org.eclipse.scava.crossflow.examples.simple.nbody.benchmark;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.math3.util.FastMath;
import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation.InvalidNumberOfCubesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.SimpleNBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.c.SimpleNBodySimulationCS257;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.ThreadedSimpleNBody;

public class NBodyBenchmark {

	private final int minSize;
	private final int maxSize;
	private final int steps;
	private final int runs;
	private final String resultsFolder;
	
	// args[4] = /Users/horacio/temp/stars/
	public static void main(String... args) {
		NBodyBenchmark b = new NBodyBenchmark(
			Integer.parseInt(args[0]),
			Integer.parseInt(args[1]),
			Integer.parseInt(args[2]),
			Integer.parseInt(args[3]),
			args[4]);
		try {
			b.run();
		} catch (CreatingBodiesException | InvalidNumberOfCubesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	public NBodyBenchmark(int minSize, int maxSize, int steps, int runs, String resultsFolder) {
		super();
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.steps = steps;
		this.runs = runs;
		this.resultsFolder = resultsFolder;
	}


	/**
	 * Run the different implementations for 10^n, 1<=n<=6, bodies for 100 steps. For each number
	 * of stars, the simulation is ran 20 times to get an average.
	 * @throws CreatingBodiesException 
	 * @throws InvalidNumberOfCubesException 
	 */
	public void run() throws CreatingBodiesException, InvalidNumberOfCubesException {
		System.out.println(String.format("Running benchmar for %s size(s), %s step(s) each, for %s run(s)", maxSize-minSize+1, steps, runs));
		Path results = Paths.get(String.format("%s/%s_results.csv",resultsFolder,steps));
		Path timing = Paths.get(String.format("%s/%s_timing.csv",resultsFolder,steps));
		printResultsHeader(results);
		printTimingHeader(timing);
		
		for (int i=minSize; i<=maxSize; i++) {
			int numbodies = (int) FastMath.pow(10.0, i);
			System.out.println(String.format("Bodies: 10^%s",i));
			for (int run = 0; run < runs; run++) {
				NBodySimulation b1 = new SimpleNBodySimulationCS257(false, false,false,true);
				b1.populateRandomly(numbodies);
				b1.runSimulation(steps);
				printResultData(results, run, b1, numbodies, "cs257");
				printTimingData(timing, run, b1, numbodies, "cs257");
				NBodySimulation b2 = new SimpleNBody(1);
				b2.populateRandomly(numbodies);
				b2.runSimulation(steps);
				printResultData(results, run, b2, numbodies, "simple");
				printTimingData(timing, run, b2,numbodies, "simple");
				NBodySimulation b3 = new ThreadedSimpleNBody();
				b3.populateRandomly(numbodies);
				b3.runSimulation(steps);
				printResultData(results, run, b3, numbodies, "threads");
				printTimingData(timing, run, b3, numbodies, "threads");
			}
		}
		System.out.println("Done");
	}
	
	private void printTimingHeader(Path filePath) {
		try (PrintWriter writer = new PrintWriter(filePath.toFile())) {
			writer.println("impl,bodies,run,prepare,acc,vel,pos,total,GFLOP/s,GB/s,global");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Results file not found", e);
		}
	}
	
	private void printTimingData(Path filePath, int run, NBodySimulation app, int numbodies, String impl) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), true))) {
			writer.format("%s,%s,%s,%s%n", impl, numbodies, run, app.getMetrics());
		} catch (IOException e) {
			throw new IllegalStateException("Error storing data to file", e);
		}
	}	
	
	private void printResultsHeader(Path filePath) {
		try (PrintWriter writer = new PrintWriter(filePath.toFile())) {
			writer.println("run,force");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Results file not found", e);
		}
	}
	
	private void printResultData(Path filePath, int run, NBodySimulation app, int numbodies, String impl) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), true))) {
			writer.format("%s,%s,%s,%s%n", impl,numbodies, run, app.getPhi());
		} catch (IOException e) {
			throw new IllegalStateException("Error storing data to file", e);
		}
	}

}
