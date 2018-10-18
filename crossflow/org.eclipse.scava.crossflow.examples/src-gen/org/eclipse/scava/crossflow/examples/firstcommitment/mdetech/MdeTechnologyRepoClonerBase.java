package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoClonerBase implements MdeTechnologyRepoEntriesConsumer{
	
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected MdeTechnologyClonedRepoEntries mdeTechnologyClonedRepoEntries;
	
	public void setMdeTechnologyClonedRepoEntries(MdeTechnologyClonedRepoEntries mdeTechnologyClonedRepoEntries) {
		this.mdeTechnologyClonedRepoEntries = mdeTechnologyClonedRepoEntries;
	}
	
	public MdeTechnologyClonedRepoEntries getMdeTechnologyClonedRepoEntries() {
		return mdeTechnologyClonedRepoEntries;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
}