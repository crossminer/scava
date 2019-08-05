package org.eclipse.scava.crossflow.examples.stars;

public class SimpleNBody implements StarSimulation {
	
	private final static float eps = 0.00125f;
	private final static float dmp = 0.995f;
	private final static float dt = 0.001f;
	
	private final int N;
	private final float[] x;
	private final float[] y;
	private final float[] z;
	private final float[] ax;
	private final float[] ay;
	private final float[] az;
	private final float[] vx;
	private final float[] vy;
	private final float[] vz;
	private final float[] m;
	
	// Timers.
	private long l0 = 0;
	private long l1 = 0;
	private long l2 = 0;
	private long l3 = 0;

	public SimpleNBody(
		int N,
		float[] x,
		float[] y,
		float[] z,
		float[] ax,
		float[] ay,
		float[] az,
		float[] vx,
		float[] vy,
		float[] vz,
		float[] m) {
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
				float rx = x[j] - x[i];
				float ry = y[j] - y[i];
				float rz = z[j] - z[i];
				float r2 = rx*rx + ry*ry + rz*rz + eps;
				float r2inv = (float) (1.0f / Math.sqrt(r2));
				float r6inv = r2inv * r2inv * r2inv;
				float s = m[j] * r6inv;
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
