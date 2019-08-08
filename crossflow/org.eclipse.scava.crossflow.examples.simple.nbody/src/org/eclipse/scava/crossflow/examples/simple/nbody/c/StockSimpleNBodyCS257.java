package org.eclipse.scava.crossflow.examples.simple.nbody.c;

public class StockSimpleNBodyCS257 implements SimpleNBodyCS257 {
	
	private final static double eps = 0.00125f;
	private final static double dmp = 0.995f;
	private final static double dt = 0.001f;
	
	private final int N;
	private final double[] x;
	private final double[] y;
	private final double[] z;
	private final double[] ax;
	private final double[] ay;
	private final double[] az;
	private final double[] vx;
	private final double[] vy;
	private final double[] vz;
	private final double[] m;
	
	// Timers.
	private long l0 = 0;
	private long l1 = 0;
	private long l2 = 0;
	private long l3 = 0;

	public StockSimpleNBodyCS257(
		int N,
		double[] x,
		double[] y,
		double[] z,
		double[] ax,
		double[] ay,
		double[] az,
		double[] vx,
		double[] vy,
		double[] vz,
		double[] m) {
		super();
		this.N = N;
		this.x = x;
		this.y = y;
		this.z = z;
		this.ax = ax;
		this.ay = ay;
		this.az = az;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		this.m = m;
	}

	@Override
	public void updateStars() {
		long t0, t1;

		// Loop 0.
		t0 = wtime();
		for (int i = 0; i < N; i++) {
			ax[i] = 0.0f;
			ay[i] = 0.0f;
			az[i] = 0.0f;
		}
		t1 = wtime();
		l0 += (t1 - t0);

	    // Loop 1.
		t0 = wtime();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				double rx = x[j] - x[i];
				double ry = y[j] - y[i];
				double rz = z[j] - z[i];
				double r2 = rx*rx + ry*ry + rz*rz + eps;
				double r2inv = (double) (1.0f / Math.sqrt(r2));
				double r6inv = r2inv * r2inv * r2inv;
				double s = m[j] * r6inv;
				ax[i] += s * rx;
				ay[i] += s * ry;
				az[i] += s * rz;
			}
		}
		t1 = wtime();
		l1 += (t1 - t0);

		// Loop 2.
		t0 = wtime();
		for (int i = 0; i < N; i++) {
			vx[i] += dmp * (dt * ax[i]);
			vy[i] += dmp * (dt * ay[i]);
			vz[i] += dmp * (dt * az[i]);
		}
		t1 = wtime();
		l2 += (t1 - t0);

		// Loop 3.
		t0 = wtime();
		for (int i = 0; i < N; i++) {
			x[i] += dt * vx[i];
			y[i] += dt * vy[i];
			z[i] += dt * vz[i];
			if (x[i] >= 1.0f || x[i] <= -1.0f) vx[i] *= -1.0f;
			if (y[i] >= 1.0f || y[i] <= -1.0f) vy[i] *= -1.0f;
			if (z[i] >= 1.0f || z[i] <= -1.0f) vz[i] *= -1.0f;

		}
		t1 = wtime();
		l3 += (t1 - t0);
	}


	private long wtime() {
		return System.nanoTime();
	}



	@Override
	public double getLoopZeroTime() {
		return l0 / 1e9;
	}



	@Override
	public double getLoopOneTime() {
		return l1 / 1e9;
	}



	@Override
	public double getLoopTwoTime() {
		return l2 / 1e9;
	}



	@Override
	public double getLoopThreeTime() {
		return l3 / 1e9;
	}


	@Override
	public double getTotalTime() {
		return (l0+l1+l2+l3) / 1e9;
	}



	@Override
	public void close() throws Exception {
		// Nothing to close
	}



	@Override
	public void init() {
		// Nothing to init
	}

}
