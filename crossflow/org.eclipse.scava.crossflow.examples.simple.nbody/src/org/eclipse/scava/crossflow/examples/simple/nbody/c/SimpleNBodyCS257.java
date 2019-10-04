package org.eclipse.scava.crossflow.examples.simple.nbody.c;

public interface SimpleNBodyCS257 extends AutoCloseable {

	void updateStars();
	void init();	
	double getLoopZeroTime();
	double getLoopOneTime();
	double getLoopTwoTime();
	double getLoopThreeTime();
	double getTotalTime();

}
