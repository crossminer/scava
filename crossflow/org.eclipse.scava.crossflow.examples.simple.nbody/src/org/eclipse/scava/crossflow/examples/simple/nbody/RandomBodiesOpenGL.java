package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Create bodies randomly
 * 
 * @author Horacio Hoyos Rodriguez
 *
 */
public class RandomBodiesOpenGL implements Bodies {
	
	private final double RAND_MAX = Integer.MAX_VALUE;
	private final int N;
	private final double pscale;
	private final double vscale;
	private final double mscale;
	
	
	public RandomBodiesOpenGL(int n) {
		this(n,1.0,1.0,1.0);
	}
	
	public RandomBodiesOpenGL(int n, double pscale, double vscale, double mscale) {
		super();
		N = n;
		this.pscale = pscale;
		this.vscale = vscale;
		this.mscale = mscale;
	}

	@Override
	public Set<NBody3DBody> createBodies() {
		Set<NBody3DBody> result = new HashSet<>();
		Random rand = new Random(42);
    	for (int i = 0; i < N; i++) {
    		result.add(new SimpleOpenGLBody(
				new StockVector3D(
					((Math.abs(Math.abs(Math.abs(rand.nextInt()))) / (double) RAND_MAX) * 2 - 1) * pscale,
					((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * pscale,
					((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * pscale),
				new StockVector3D(
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale,
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale,
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale),
				(Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * mscale,
				(float) (Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX),
				(float) (Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX),
				(float) (Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX))
				);
		}
		return result;
	}
	

}
