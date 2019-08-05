package org.eclipse.scava.crossflow.examples.stars;

import java.util.Calendar;
import java.util.Random;

public class Stars { //extends SimpleApplication {

	private static final float RAND_MAX = Integer.MAX_VALUE;
	private final static float eps = 0.00125f;
	
	private final int N;
	private final int steps;
	private final boolean vis;
	private float[] x;
	private float[] y;
	private float[] z;
	private float[] ax;
	private float[] ay;
	private float[] az;
	private float[] vx;
	private float[] vy;
	private float[] vz;
	private float[] m;
	private float[] c;
	
	// Current timestep.
	int t = 0;
	
	//private StarSimulation compute;
	
	public static void main(String[] args) {
		// Parse command line options.
		if (args.length != 3) {
			System.out.println("Usage: Stars [# stars] [# timesteps] [visualisation (0 or 1)]\n");
			System.out.println("Eg   : Stars 10000 100 1\n");
			return;
		}
		int N = Integer.parseInt(args[0]);
		int steps = Integer.parseInt(args[1]);
		boolean vis = Boolean.parseBoolean(args[2]);

	    // Print a banner.
	    System.out.println("\n");
		System.out.println("Simple N-body code\n");
		System.out.println("-----------------------------------------\n");
		System.out.println(String.format(" Starting simulation of %d stars for %d timesteps.\n",  N, steps));
	
		Stars app = new Stars(N, steps, vis);
		app.initSimulation();
	}
	
	public Stars(int N, int steps, boolean vis) {
		this.N = N;
		this.steps = steps;
		this.vis = vis;
		
	}
	
	public void initSimulation() {
		init();
		if (vis) {
		    try(StarSimulation compute = new Universe(N, x, y, z, ax, ay, az, vx, vy, vz, m, c)) {
		    	compute.init();
		    	while (!updateStars(compute));
		    } catch (Exception e) {
				e.printStackTrace();
			}
		} else {
	        SimpleNBody compute = new SimpleNBody(N, x, y, z, ax, ay, az, vx, vy, vz, m);
	        compute.init();
	    	while (!updateStars(compute));
		}

    }
    
    private void init() {
    	x = new float[N];
    	y = new float[N];
    	z = new float[N];
    	ax = new float[N];
    	ay = new float[N];
    	az = new float[N];
    	vx = new float[N];
    	vy = new float[N];
    	vz = new float[N];
    	m = new float[N];
    	
    	// Color!
    	c = new float[3*N];
    	
    	// Initialise array contents.
    	// srand(42) simply to make results repeatable. :)
    	Random rand = new Random(42);
    	float scale = 1.0f;
    	float vscale = 1.0f;
    	float mscale = 1.0f;
    	for (int i = 0; i < N; i++) {
    		x[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * scale;
    		y[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * scale;
    		z[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * scale;
    		ax[i] = 0.0f;
    		ay[i] = 0.0f;
    		az[i] = 0.0f;
    		vx[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * vscale;
    		vy[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * vscale;
    		vz[i] = ((rand.nextInt() / (float) RAND_MAX) * 2 - 1) * vscale;
    		m[i] = (rand.nextInt() / (float) RAND_MAX) * mscale;
    		c[3*i+0] = (rand.nextInt() / (float) RAND_MAX);
    		c[3*i+1] = (rand.nextInt() / (float) RAND_MAX);
    		c[3*i+2] = (rand.nextInt() / (float) RAND_MAX);
    	}
    }
    
    /**
    * Call the compute() function to update the frame.
    * Sleep to maintain 60fps.
    */
	private static float FPS = 60.0f;
	
	private	boolean updateStars(StarSimulation simulation) {
  		double t_start = wtime();
  		simulation.updateStars();
   		double t_end = wtime();
   		if (t_end - t_start < (1.0f/FPS)) {
   			System.out.println("Faster than FPS");
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
   
	private boolean check_exit(int i, StarSimulation simulation) {
		if (t >= steps) {

		    // Print results and stuff.
		    System.out.println("\n");
		    System.out.println(String.format(" Loop 0 = %f seconds.\n", simulation.getLoopZeroTime()));
		    System.out.println(String.format(" Loop 1 = %f seconds.\n", simulation.getLoopOneTime()));
		    System.out.println(String.format(" Loop 2 = %f seconds.\n", simulation.getLoopTwoTime()));
		    System.out.println(String.format(" Loop 3 = %f seconds.\n", simulation.getLoopThreeTime()));
		    System.out.println(String.format(" Total  = %f seconds.\n", simulation.getTotalTime()));
		    System.out.println("\n");
		    double flops = 20.0f * (double) N * (double) (N-1) * (double) steps;
		    System.out.println(String.format(" GFLOP/s = %f\n", flops / 1000000000.0f / (simulation.getTotalTime())));

		    double bytes = 4.0f * (double) N * 10.0f * (double) steps;
		    System.out.println(String.format(" GB/s = %f\n", bytes / 1000000000.0f / (simulation.getTotalTime())));
		    System.out.println("\n");

		    // Verify solution.
		    verify();
		    System.out.println("\n");

		    // Tidy up.
		    // cleanup();
	        //    exit(0);
	        return true;
		}
		return false;
	}
	

	private void verify() {
		float phi = 0.0f;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				float rx = x[j] - x[i];
				float ry = y[j] - y[i];
				float rz = z[j] - z[i];
				float r2 = rx*rx + ry*ry + rz*rz + eps;
				float r2inv = (float) (1.0 / Math.sqrt(r2));
				float r6inv = r2inv * r2inv * r2inv;
				phi += m[j] * r6inv;
			}
		}

		System.out.println(String.format(" Answer = %f\n", phi));
	}

	/**
	 * Return time in seconds in Epoch.
	 */
	private double wtime() {
		return Calendar.getInstance().getTimeInMillis() / 1000l;
	}

}
