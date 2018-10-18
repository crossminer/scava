package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoFileCounterBase implements MdeTechnologyClonedRepoEntriesConsumer{
	
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries;
	
	public void setMdeTechnologyRepoFileCountEntries(MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries) {
		this.mdeTechnologyRepoFileCountEntries = mdeTechnologyRepoFileCountEntries;
	}
	
	public MdeTechnologyRepoFileCountEntries getMdeTechnologyRepoFileCountEntries() {
		return mdeTechnologyRepoFileCountEntries;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
}