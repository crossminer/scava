package org.eclipse.scava.crossflow.examples.simple.nbody.c;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleNBodySimulationCS257 { //extends SimpleApplication {

	private static final int RAND_MAX = Integer.MAX_VALUE;
	private final static double eps = 0.00125f;
	private static Path results;
	private static Path data;
	//private final String fmt = "Body %d : % 8.6f  % 8.6f  % 8.6f | % 8.6f  % 8.6f  % 8.6f\n";
	
	private final String fmt = "%d,%d,%f,%f,%f,%f,%f,%f%n";
	
	
	private final int N;
	private final int steps;
	private final boolean vis;
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
	
	//private StarSimulation compute;
	
	public static void main(String[] args) {
		// Parse command line options.
		if (args.length < 3) {
			System.out.print("Usage: Stars [# stars] [# timesteps] [visualisation (0 or 1)]\n");
			System.out.print("Eg   : Stars 10000 100 1\n");
			return;
		}
		int N = Integer.parseInt(args[0]);
		int steps = Integer.parseInt(args[1]);
		boolean vis = Boolean.parseBoolean(args[2]);
		if (args.length >= 4) {
			results = Paths.get(args[3]);
		}
		if (args.length >= 5) {
			data = Paths.get(args[4]);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				JsonNode jsonNode = objectMapper.readTree(data.toFile());
				N = jsonNode.size();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	    // Print a banner.
	    System.out.print("\n");
		System.out.print("Simple N-body code\n");
		System.out.print("-----------------------------------------\n");
		System.out.print(String.format(" Starting simulation of %d stars for %d timesteps.\n",  N, steps));
	
		SimpleNBodySimulationCS257 app = new SimpleNBodySimulationCS257(N, steps, vis);
		app.initSimulation();
	}
	
	public SimpleNBodySimulationCS257(int N, int steps, boolean vis) {
		this.N = N;
		this.steps = steps;
		this.vis = vis;
		
	}
	
	public void initSimulation() {
		init();
		printHeader();
		if (vis) {
		    try(SimpleNBodyCS257 compute = new OpenGLSimpleNBodyCS257(N, x, y, z, ax, ay, az, vx, vy, vz, m, c)) {
		    	compute.init();
		    	while (!updateStars(compute));
		    } catch (Exception e) {
				e.printStackTrace();
			}
		} else {
	        StockSimpleNBodyCS257 compute = new StockSimpleNBodyCS257(N, x, y, z, ax, ay, az, vx, vy, vz, m);
	        compute.init();
	    	while (!updateStars(compute));
		}

    }
   

	private void init() {
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
    	c = new double[3*N];
    	
    	// Initialise array contents.
    	// srand(42) simply to make results repeatable. :)
    	if (data == null) {
    		
        	
	    	Random rand = new Random(42);
	    	double scale = 1.0f;
	    	double vscale = 1.0f;
	    	double mscale = 1.0f;
	    	for (int i = 0; i < N; i++) {
	    		x[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * scale;
	    		y[i] = (((Math.abs(rand.nextInt()) / (double) RAND_MAX) * 2) - 1) * scale;
	    		z[i] = (((Math.abs(rand.nextInt())  / (double) RAND_MAX) * 2) - 1) * scale;
	    		ax[i] = 0.0f;
	    		ay[i] = 0.0f;
	    		az[i] = 0.0f;
	    		vx[i] = (((Math.abs(rand.nextInt())  / (double) RAND_MAX) * 2) - 1) * vscale;
	    		vy[i] = (((Math.abs(rand.nextInt())  / (double) RAND_MAX) * 2) - 1) * vscale;
	    		vz[i] = (((Math.abs(rand.nextInt())  / (double) RAND_MAX) * 2) - 1) * vscale;
	    		m[i] = (Math.abs(rand.nextInt())  / (double) RAND_MAX) * mscale;
	    		c[3*i+0] = (Math.abs(rand.nextInt())  / (double) RAND_MAX);
	    		c[3*i+1] = (Math.abs(rand.nextInt())  / (double) RAND_MAX);
	    		c[3*i+2] = (Math.abs(rand.nextInt())  / (double) RAND_MAX);
	    	}
    	}
    	else {
    		try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(data.toFile());
				System.out.println(jsonNode.isArray());
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
		    		c[3*i+0] = body.get("cr").asDouble();
		    		c[3*i+1] = body.get("cg").asDouble();
		    		c[3*i+2] = body.get("cb").asDouble();
		    		i++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    /**
    * Call the compute() function to update the frame.
    * Sleep to maintain 60fps.
    */
	private static double FPS = 60.0f;
	
	private	boolean updateStars(SimpleNBodyCS257 simulation) {
		printResults(t);
		double t_start = wtime();
  		simulation.updateStars();
   		double t_end = wtime();
   		if (t_end - t_start < (1.0f/FPS)) {
   			int usecs = (int) (((1.0f/FPS) - (t_end - t_start)) * 1E6);
			try {
				Thread.sleep((long)usecs/1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
   		}

   		//if (vis) glutPostRedisplay();
   		return check_exit(t++, simulation);
   }
   
	private boolean check_exit(int i, SimpleNBodyCS257 simulation) {
		if (t >= steps) {

		    // Print results and stuff.
		    System.out.print("\n");
		    System.out.print(String.format(" Loop 0 = %f seconds.\n", simulation.getLoopZeroTime()));
		    System.out.print(String.format(" Loop 1 = %f seconds.\n", simulation.getLoopOneTime()));
		    System.out.print(String.format(" Loop 2 = %f seconds.\n", simulation.getLoopTwoTime()));
		    System.out.print(String.format(" Loop 3 = %f seconds.\n", simulation.getLoopThreeTime()));
		    System.out.print(String.format(" Total  = %f seconds.\n", simulation.getTotalTime()));
		    System.out.print("\n");
		    double flops = 20.0f * (double) N * (double) (N-1) * (double) steps;
		    System.out.print(String.format(" GFLOP/s = %f\n", flops / 1000000000.0f / (simulation.getTotalTime())));

		    double bytes = 4.0f * (double) N * 10.0f * (double) steps;
		    System.out.print(String.format(" GB/s = %f\n", bytes / 1000000000.0f / (simulation.getTotalTime())));
		    System.out.print("\n");

		    // Verify solution.
		    verify();
		    System.out.print("\n");

		    // Tidy up.
		    // cleanup();
	        //    exit(0);
	        return true;
		}
		return false;
	}
	

	private void verify() {
		double phi = 0.0f;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				double rx = x[j] - x[i];
				double ry = y[j] - y[i];
				double rz = z[j] - z[i];
				double r2 = rx*rx + ry*ry + rz*rz + eps;
				double r2inv = (double) (1.0 / Math.sqrt(r2));
				double r6inv = r2inv * r2inv * r2inv;
				phi += m[j] * r6inv;
			}
		}

		System.out.print(String.format(" Answer = %f\n", phi));
	}

	/**
	 * Return time in seconds in Epoch.
	 */
	private double wtime() {
		return Calendar.getInstance().getTimeInMillis() / 1000l;
	}
	
    private void printHeader() {
		if (results != null) {
			try (PrintWriter writer = new PrintWriter(results.toFile())) {
			    writer.println("iteration,body,x,y,z,vx,vx,vz");
			} catch (FileNotFoundException e) {
				// dont bother with the rest
				results = null;
			}
		}
	}
	
    private void printResults(int iteration) {
    	if (results != null) {
    		try (PrintWriter writer = new PrintWriter(new FileWriter(results.toFile(), true))) {
			    for (int i = 0; i < N; ++i) {
			    	writer.printf(fmt, iteration,i + 1,x[i], y[i], z[i], vx[i], vy[i], vz[i]);
		        }
			} catch (FileNotFoundException e) {
				// dont bother with the rest
				results = null;
			} catch (IOException e1) {
				// dont bother with the rest
				results = null;
			}
		}
        
    }

}
