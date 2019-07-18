package org.eclipse.scava.plugin.usermonitoring.metric.term;

public enum Duration implements ITerm{
	
	SHORT(1,24),
	MID(24,7),
	LONG(168,4),
	OVERVIEW(1,672);
	
	private final int duration;
	private final int count;

	private Duration(int duration,int count) {
		this.duration = duration;
		this.count = count;
	}
	
	public int getDuration() {
		return duration;
	}
	
	@Override
	public int getCount() {
		return count;
	}

}
