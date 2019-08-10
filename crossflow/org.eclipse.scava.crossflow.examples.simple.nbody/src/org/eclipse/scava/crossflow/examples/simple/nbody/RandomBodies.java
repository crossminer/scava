/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.scava.crossflow.examples.simple.nbody;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Create bodies randomly.
 *
 * @author Horacio Hoyos Rodriguez
 */
public class RandomBodies implements Bodies {
	
	private final double RAND_MAX = Integer.MAX_VALUE;
	private final int N;
	private final double pscale;
	private final double vscale;
	private final double mscale;
	
	
	public RandomBodies(int n) {
		this(n,1.0,1.0,1.0);
	}
	
	public RandomBodies(int n, double pscale, double vscale, double mscale) {
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
    		result.add(new SofteningNBodyBody(
				new StockVector3D(
					((Math.abs(Math.abs(Math.abs(rand.nextInt()))) / (double) RAND_MAX) * 2 - 1) * pscale,
					((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * pscale,
					((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * pscale),
				new StockVector3D(
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale,
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale,
						((Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * 2 - 1) * vscale),
				(Math.abs(Math.abs(rand.nextInt())) / (double) RAND_MAX) * mscale));
		}
		return result;
	}
	

}
