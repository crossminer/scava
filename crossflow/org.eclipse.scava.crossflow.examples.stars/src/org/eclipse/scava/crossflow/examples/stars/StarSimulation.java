package org.eclipse.scava.crossflow.examples.stars;

public interface StarSimulation extends AutoCloseable {

	void updateStars();
	void init();	
	double getLoopZeroTime();
	double getLoopOneTime();
	double getLoopTwoTime();
	double getLoopThreeTime();
	double getTotalTime();

}
