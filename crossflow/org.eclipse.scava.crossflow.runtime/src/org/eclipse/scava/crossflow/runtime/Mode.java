package org.eclipse.scava.crossflow.runtime;

public enum Mode {
	
	MASTER_BARE(true, false),
	MASTER(true, true),
	WORKER(false, true),
	API(false, false);
	
	private final boolean isMaster;
	private final boolean isWorker;
	
	private Mode(boolean isMaster, boolean isWorker) {
		this.isMaster = isMaster;
		this.isWorker = isWorker;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public boolean isWorker() {
		return isWorker;
	}
	
}