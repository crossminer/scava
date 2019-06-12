package org.eclipse.scava.crossflow.dt;

import org.eclipse.jface.action.IAction;

public class DeployArtifactsAndRun extends DeployArtifacts {

	@Override
	public void run(IAction action) {
		startImmediately = true;
		super.run(action);
	}

}
