package org.eclipse.scava.crossflow.examples.simple.nbody.cf;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBody3DBody;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation.InvalidNumberOfCubesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.RandomBodies;
import org.eclipse.scava.crossflow.examples.simple.nbody.StockCuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.Vector3D;
import org.eclipse.scava.crossflow.examples.simple.nbody.threads.StockCuboids;

public class Step extends OpinionatedStepBase {

	private final double eps = 0.00125;
	private boolean running = false;
	private int stepsCnt = 0;
	private int steps = 0;
	private double dmp;
	private double timeDelta;
	
	protected Collection<String> activeWorkerIds = new HashSet<>();
	private Collection<CuboidCoordinates> stepCuboids;
	
	private Duration prprDrtn;
	private Duration calcAccelDrtn;
	private Duration calcVelDrtn;
	private Duration calcPosDrtn;
	private Duration overHeadDrtn;
	List<NBody3DBody> stepBodies;
	private long memSize;
	private List<NBody3DBody> allBodies;
	private long start;
	private int cuboids;
	private boolean threaded;
	private int size;
	

	@Override
	public void consumeLines(Line line) throws Exception {
		
		/* size,steps,dmp,timeDelta,cuboids,threaded */
		String[] values = line.text.split(",");
		System.out.println("Step.consumeLines " + Arrays.toString(values));
		assert values.length == 4;
		size = Integer.parseInt(values[0]);
		stepsCnt = steps = Integer.parseInt(values[1]);
		dmp = Double.parseDouble(values[2]);
		timeDelta = Double.parseDouble(values[3]);
		cuboids = Integer.parseInt(values[4]);
		threaded = Boolean.parseBoolean(values[5]);
		
		allBodies = new RandomBodies(size).createBodies();
		stepBodies = new ArrayList<>();
		prprDrtn = Duration.ZERO;
		calcAccelDrtn = Duration.ZERO;
		calcVelDrtn = Duration.ZERO;
		calcPosDrtn = Duration.ZERO;
		start = System.nanoTime();
		distributeCuboids();
		running = true;
		
	}
	
	@Override
	public boolean acceptInput(Line input) {
		
		return !running;
	}
	
	@Override
	public void consumeResults(StepResults r) throws Exception {
		// FIXME How to deal with workers quitting half way a job?	
		System.out.println("Step consume results " +  LocalDateTime.now());
		prprDrtn = prprDrtn.plus(r.getDurations().prepareDrtn());
		calcAccelDrtn = calcAccelDrtn.plus(r.getDurations().calcAccelDrtn());
		calcVelDrtn = calcVelDrtn.plus(r.getDurations().calcVelDrtn());
		calcPosDrtn = calcPosDrtn.plus(r.getDurations().calcPosDrtn());
		// Only count if durations are OK
//		System.out.println("stepCuboids " + stepCuboids);
		stepCuboids.remove(r.getCoordiantes());
		System.out.println("stepCuboids modified" + stepCuboids.size());
		stepBodies.addAll(r.getBodies());
		memSize += r.getMemUsed();
		// How to keep waiting for results? I guess if the queue is not empty?
		if (stepCuboids.isEmpty()) {
//			System.out.println("Step consume results next step");
			
			allBodies = stepBodies;
			System.out.println("all cuboids finished. Total bodies: " + allBodies.size());
			stepsCnt--;
			if (stepsCnt > 0) {
				stepBodies = new ArrayList<>();
				distributeCuboids();
			}
			else {
				running = false;
				double[] perf = calculatePerformance();
				overHeadDrtn = Duration.ofNanos(System.nanoTime() - start);
				sendToPerformance(new Line(
						String.format("%d,%d,%f,%f,%f,%f,%f,%f,%f,%f,%f",
								size,
								steps,
								prprDrtn.toNanos()/1e9, 
								calcAccelDrtn.toNanos()/1e9, 
								calcVelDrtn.toNanos()/1e9,
								calcPosDrtn.toNanos()/1e9,
								getTotalTime(),
								perf[0], perf[1], perf[2],  // flops, mem, verify
								overHeadDrtn.toNanos()/1.0e9)));
			}
		}
	}

	
	
	@Override
	public boolean acceptInput(StepResults input) {
		return running;
	}
	
	private void distributeCuboids() throws InvalidNumberOfCubesException {
		System.out.println("Step.distributeCuboids " + cuboids);
		stepCuboids = new StockCuboids(new StockCuboidCoordinates()).setupCuboids(cuboids);
		for (CuboidCoordinates c : stepCuboids) {
			StepData stepData = new StepData(
					dmp,
					timeDelta,
					allBodies,
					c,
					threaded);
			sendToUniverse(stepData);			
		}
	}
	
	
	private double[] calculatePerformance() {
		double[] result = new double[3];
		//flops = (20.0f * (double) N * (double) (N - 1) * (double) steps) / 1000000000.0f / getTotalTime();
		// 20 floating point operations
		// 14 to calculate acceleration
		// 6 for velocity and position
		// flops
		double totalTime = getTotalTime();
		System.out.println("calculatePerformance " + size);
		System.out.println("Gflops" + (((14.0*size*size + 6.0*size) * steps)/ 1.0e9 / totalTime));
		System.out.println("Mflops " + (((14.0*size*size + 6.0*size) * steps)/ 1.0e6 / totalTime));
		result[0] = ((14.0*size*size + 6.0*size) * stepsCnt)/ 1.0e9 / totalTime;
		// We calculated the mem size from the bumber of cuboids and their individual size
		//bytes = (4.0f * (double) N * 10.0f * (double) steps)/ 1000000000.0f / getTotalTime();
		result[1] = memSize / 1.0e6 / totalTime;
		// Verify solution.
		result[2] = 0.0f;
		for (NBody3DBody body1 : allBodies) {
			for (NBody3DBody body2 : allBodies) {
				Vector3D distance = body2.position().subtract(body1.position());
				double r2 = FastMath.sqrt(distance.normSq() + eps);
				double r2inv = 1.0 / r2;
				double r6inv = r2inv * r2inv * r2inv;
				result[2] += body2.mass() * r6inv;

			}
		}
		return result;
	}
	
	private double getTotalTime() {
		return prprDrtn.plus(calcAccelDrtn).plus(calcVelDrtn).plus(calcPosDrtn).toNanos() / 1.0e9;
	}
	

}
