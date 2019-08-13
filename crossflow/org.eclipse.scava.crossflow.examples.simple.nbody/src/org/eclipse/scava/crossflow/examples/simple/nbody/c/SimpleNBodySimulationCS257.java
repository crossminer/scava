package org.eclipse.scava.crossflow.examples.simple.nbody.c;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;
import org.eclipse.scava.crossflow.examples.simple.nbody.Bodies.CreatingBodiesException;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation;
import org.github.jamm.MemoryMeter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleNBodySimulationCS257 implements NBodySimulation {

	private static final int RAND_MAX = Integer.MAX_VALUE;
	private final static double eps = 0.00125f;
	/**
	 * Call the compute() function to update the frame. Sleep to maintain 60fps.
	 */
	private static double FPS = 60.0f;

	private int N;
	private final boolean vis;
	private final boolean verbose;
	private final boolean quiet;
	private final boolean details;
	private final List<String> stepDetails = new ArrayList<>();
	
	private final String fmt = "%d,%d,%f,%f,%f,%f,%f,%f%n";
	private double[] x;
	private double[] y;
	private double[] z;
	private double[] ax;
	private double[] ay;
	private double[] az;
	private double[] vx;
	private double[] vy;
	private double[] vz;
	private double[] m;
	private double[] c;

	// Current timestep.
	int t = 0;
	private double phi;
	private double loopZeroTime;
	private double loopOneTime;
	private double loopTwoTime;
	private double loopThreeTime;
	private double totalTime;
	private double flops;
	private double bytes;
	private Duration overHeadDrtn;
	

	public static void main(String[] args) {
		Option verboseOpt = new Option("verbose", "be extra verbose");
		Option quietOpt = new Option("quiet", "be extra quiet");
		Option starsOpt = Option
				.builder("s")
				.required()
				.hasArg().argName("stars")
				.desc("number of stars")
				.type(PatternOptionBuilder.NUMBER_VALUE).build();
		Option timestepsOpt = Option
				.builder("t")
				.required()
				.hasArg().argName("steps")
				.desc("number of simulation timesteps")
				.type(PatternOptionBuilder.NUMBER_VALUE).build();
		Option visualizeOpt = Option
				.builder("v")
				.desc("Use OpenGL to visualize the simulation").build();
		Option stardataOpt = Option
				.builder("data")
				.hasArg().argName("<path>")
				.desc("read star simulation from file (JSON). Parameter 'stars' will be overriden by the number of stars in the file").build();
		Option detailsOpt = Option
				.builder("details")
				.hasArg().argName("<path>")
				.desc("store simulation details to the given file").build();
		Option metricsOpt = Option
				.builder("timing")
				.hasArg().argName("<path>")
				.desc("store performance details to the given file").build();
		Option resultsOpt = Option
				.builder("results")
				.hasArg().argName("<path>")
				.desc("store results to the given file")
				.build();
		Option benchmarkOpt = Option
				.builder("bench")
				.hasArg().argName("steps")
				.desc("number of iterations for benchmark")
				.type(PatternOptionBuilder.NUMBER_VALUE).build();

		final Options options = new Options();
		options.addOption(starsOpt);
		options.addOption(timestepsOpt);
		options.addOption(visualizeOpt);
		options.addOption(stardataOpt);
		options.addOption(detailsOpt);
		options.addOption(resultsOpt);
		options.addOption(metricsOpt);
		options.addOption(benchmarkOpt);
		options.addOption(verboseOpt);
		options.addOption(quietOpt);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			// parse the command line arguments
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			wrongArguments(options, exp);
			System.exit(-1);
		}

		if (!line.hasOption(quietOpt.getOpt())) {
			// Print a banner.
			System.out.print("\n");
			System.out.print("Simple N-body Star simulation\n");
			System.out.print("-----------------------------------------\n");
			try {
				System.out.print(String.format(" Starting simulation of %d stars for %d timesteps.\n",
						line.getParsedOptionValue(starsOpt.getOpt()), line.getParsedOptionValue(timestepsOpt.getOpt())));
			} catch (ParseException e) {
				wrongArguments(options, e);
				System.exit(-1);
			}
		}
		
		Path detailsPath = null;
		Path resultsPath = null;
		Path metricsPath = null;
		try {
			detailsPath = line.hasOption(detailsOpt.getOpt()) ? Paths.get((String) line.getParsedOptionValue(detailsOpt.getOpt())) : null;
			resultsPath = line.hasOption(resultsOpt.getOpt()) ? Paths.get((String) line.getParsedOptionValue(resultsOpt.getOpt())) : null;
			metricsPath = line.hasOption(metricsOpt.getOpt()) ? Paths.get((String) line.getParsedOptionValue(metricsOpt.getOpt())) : null;
		} catch (ParseException e) {
			wrongArguments(options, e);
			System.exit(-1);
		}
		SimpleNBodySimulationCS257 app = null;
		app = new SimpleNBodySimulationCS257(
					line.hasOption(visualizeOpt.getOpt()),
					detailsPath != null,
					line.hasOption(verboseOpt.getOpt()),
					line.hasOption(quietOpt.getOpt()));
		// Print headers in output files
		if (detailsPath != null) {
			try (PrintWriter writer = new PrintWriter(detailsPath.toFile())) {
				writer.println("run,iteration,body,x,y,z,vx,vy,vz,ax,ay,az");
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("detailsPath file not found", e);
			}
		}
		if (metricsPath != null) {
			try (PrintWriter writer = new PrintWriter(metricsPath.toFile())) {
				writer.println("run,prepare,acc,vel,pos,total,GFLOP/s,GB/s,global");
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Results file not found", e);
			}
		}
		if (resultsPath != null) {
			try (PrintWriter writer = new PrintWriter(resultsPath.toFile())) {
				writer.println("run,force");
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Results file not found", e);
			}
		}

		int runs = 0;
		int steps = 0;
		try {
			runs = line.hasOption(benchmarkOpt.getOpt()) ? ((Long) line.getParsedOptionValue(benchmarkOpt.getOpt())).intValue() : 1;
			steps = ((Long) line.getParsedOptionValue(timestepsOpt.getOpt())).intValue();
			if (line.hasOption(stardataOpt.getOpt())) {
				app.populateFromJson(Paths.get((String) line.getParsedOptionValue(stardataOpt.getOpt())));		
			}
			else {
				app.populateRandomly(((Long) line.getParsedOptionValue(starsOpt.getOpt())).intValue());
			}
		} catch (ParseException e) {
			wrongArguments(options, e);
			System.exit(-1);
		} catch (CreatingBodiesException e) {
			throw new IllegalStateException(e);
		}
		
		for (int run = 0; run < runs; run++) {
			app.runSimulation(steps);
			if (detailsPath != null) {
				try (PrintWriter writer = new PrintWriter(new FileWriter(detailsPath.toFile(), true))) {
					for (String sd : app.stepDetails()) {
						writer.format("%s,%s%n", run, sd);
					}
				} catch (IOException e) {
					throw new IllegalStateException("Error storing data to file", e);
				}
			}
			if (metricsPath != null) {
				try (PrintWriter writer = new PrintWriter(new FileWriter(metricsPath.toFile(), true))) {
					writer.format("%s,%s%n", run, app.getMetrics());
				} catch (IOException e) {
					throw new IllegalStateException("Error storing data to file", e);
				}
			}
			if (resultsPath != null) {
				try (PrintWriter writer = new PrintWriter(new FileWriter(resultsPath.toFile(), true))) {
					writer.format("%s,%s%n", run, app.getPhi());
				} catch (IOException e) {
					throw new IllegalStateException("Error storing data to file", e);
				}
			}
		}
	}


	public SimpleNBodySimulationCS257(
		boolean vis,
		boolean details,
		boolean verbose,
		boolean quiet) {
		super();
		this.vis = vis;
		this.details = details;
		this.verbose = verbose;
		this.quiet = quiet;
	}

	public void runSimulation(int steps) {
		t = 0;
		long start = 0;
		if (vis) {
			try (SimpleNBodyCS257 compute = new OpenGLStars(N, x, y, z, ax, ay, az, vx, vy, vz, m, c)) {
				compute.init();
				start = System.nanoTime();
				do {
					updateStars(compute);
					t++;
				} while (!check_exit(steps, compute));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Stars compute = new Stars(N, x, y, z, ax, ay, az, vx, vy, vz, m);
			compute.init();
			start = System.nanoTime();
			do {
				updateStars(compute);
				t++;
			} while (!check_exit(steps, compute));
		}
		overHeadDrtn = Duration.ofNanos(System.nanoTime() - start);
		if (!quiet) {
			// Print results and stuff.
			System.out.print("\n");
			System.out.print(String.format(" Loop 0 = %f seconds.\n", loopZeroTime));
			System.out.print(String.format(" Loop 1 = %f seconds.\n", loopOneTime));
			System.out.print(String.format(" Loop 2 = %f seconds.\n", loopTwoTime));
			System.out.print(String.format(" Loop 3 = %f seconds.\n", loopThreeTime));
			System.out.print(String.format(" Total  = %f seconds.\n", totalTime));
			System.out.print("\n");
			System.out.print(String.format(" GFLOP/s = %f\n", flops ));	
			System.out.print(String.format(" GB/s = %f\n", bytes  ));
			System.out.print("\n");
			System.out.print(String.format(" Total time = %f seconds.\n", overHeadDrtn.toNanos()/1.0e9));
			System.out.print(String.format(" Answer = %f\n", phi));
			System.out.print("\n");
		}
	}
	
	private void updateStars(SimpleNBodyCS257 simulation) {
		storeStepData(t);
		double t_start = wtime();
		simulation.updateStars();
		double t_end = wtime();
		if (t_end - t_start < (1.0f / FPS)) {
			int usecs = (int) (((1.0f / FPS) - (t_end - t_start)) * 1E6);
			try {
				Thread.sleep((long) usecs / 1000);
			} catch (InterruptedException e) {
				// No foul
			}
		}
	}
	
	private boolean check_exit(int steps, SimpleNBodyCS257 simulation) {
		if (t >= steps) {
			loopZeroTime = simulation.getLoopZeroTime();
			loopOneTime = simulation.getLoopOneTime();
			loopTwoTime = simulation.getLoopTwoTime();
			loopThreeTime = simulation.getLoopThreeTime();
			totalTime = simulation.getTotalTime();
			// 20 floating point operations
			// 14 to calculate acceleration
			// 6 for velocity and position
			flops = ((14*N*N + 6*N) * steps)/ 1000000000.0f / simulation.getTotalTime();
			// 10 Arrays of size 10;
			MemoryMeter meter = new MemoryMeter();
		    long s = meter.measure(new double[N]);
			bytes = (s * 10.0f * steps) / 1000000.0f / simulation.getTotalTime();
			// Verify solution.
			verify();			
			return true;
		}
		return false;
	}
	

	
	@Override
	public String getMetrics() {
		return String.format("%f,%f,%f,%f,%f,%f,%f,%f",
		loopZeroTime, loopOneTime, loopTwoTime, loopThreeTime, totalTime,
		flops, bytes, overHeadDrtn.toNanos()/1.0e9);
	}

	private void verify() {
		phi = 0.0f;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				double rx = x[j] - x[i];
				double ry = y[j] - y[i];
				double rz = z[j] - z[i];
				double r2 = rx * rx + ry * ry + rz * rz + eps;
				double r2inv = (double) (1.0 / Math.sqrt(r2));
				double r6inv = r2inv * r2inv * r2inv;
				phi += m[j] * r6inv;
			}
		}
	}

	/**
	 * Return time in seconds in Epoch.
	 */
	private double wtime() {
		return Calendar.getInstance().getTimeInMillis() / 1000l;
	}

	private void storeStepData(int iteration) {
		if (details || verbose) {
			for (int i = 0; i < N; ++i) {
				String sd = String.format(fmt, iteration, i + 1, x[i], y[i], z[i], vx[i], vy[i], vz[i], ax[i], ay[i], az[i]);
				if (verbose) {
					System.out.println(sd);
				}
				if (details) {
					stepDetails.add(sd);
				}
			}
		}
	}
	
	@Override
	public double getPhi() {
		return phi;
	}

	private List<String> stepDetails() {
		return stepDetails;
	}

	private static void wrongArguments(final Options options, ParseException e) {
		// oops, something went wrong
		System.err.println("Some of the arguments where missing or wrong: " + e.getMessage());
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(null);
		formatter.printHelp("java -jar stars.jar", "\nN-body Simulation of Stars\n", options,
				"\nEnjoY!.",
				true);
	}



	@Override
	public void populateFromJson(Path data) throws CreatingBodiesException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(data.toFile());
			N = jsonNode.size();
			x = new double[N];
			y = new double[N];
			z = new double[N];
			ax = new double[N];
			ay = new double[N];
			az = new double[N];
			vx = new double[N];
			vy = new double[N];
			vz = new double[N];
			m = new double[N];
			// Color!
			c = new double[3 * N];
			int i = 0;
			for (final JsonNode body : jsonNode) {
				x[i] = body.get("x").asDouble();
				y[i] = body.get("y").asDouble();
				z[i] = body.get("z").asDouble();
				ax[i] = body.get("ax").asDouble();
				ay[i] = body.get("ay").asDouble();
				az[i] = body.get("az").asDouble();
				vx[i] = body.get("vx").asDouble();
				vy[i] = body.get("vy").asDouble();
				vz[i] = body.get("vz").asDouble();
				m[i] = body.get("m").asDouble();
				c[3 * i + 0] = body.get("cr").asDouble();
				c[3 * i + 1] = body.get("cg").asDouble();
				c[3 * i + 2] = body.get("cb").asDouble();
				i++;
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Json file has errors.", e);
		}
	}


	@Override
	public void populateRandomly(int size) throws CreatingBodiesException {
		populateRandomly(size, 1.0f, 1.0f, 1.0f);
	}


	@Override
	public void populateRandomly(int size, double pscale, double vscale, double mscale) throws CreatingBodiesException {
		N = size;
		x = new double[size];
		y = new double[size];
		z = new double[size];
		ax = new double[size];
		ay = new double[size];
		az = new double[size];
		vx = new double[size];
		vy = new double[size];
		vz = new double[size];
		m = new double[size];
		c = new double[3 * size];
		
		// srand(42) simply to make results repeatable. :)
		Random rand = new Random(42);
		
		for (int i = 0; i < size; i++) {
			x[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * pscale;
			y[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * pscale;
			z[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * pscale;
			ax[i] = 0.0f;
			ay[i] = 0.0f;
			az[i] = 0.0f;
			vx[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * vscale;
			vy[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * vscale;
			vz[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * vscale;
			m[i] = (Math.abs(rand.nextInt()) / (double) RAND_MAX) * mscale;
			if (vis) {
				c[3 * i + 0] = (Math.abs(rand.nextInt()) / (double) RAND_MAX);
				c[3 * i + 1] = (Math.abs(rand.nextInt()) / (double) RAND_MAX);
				c[3 * i + 2] = (Math.abs(rand.nextInt()) / (double) RAND_MAX);
			}
		}
	}

}
