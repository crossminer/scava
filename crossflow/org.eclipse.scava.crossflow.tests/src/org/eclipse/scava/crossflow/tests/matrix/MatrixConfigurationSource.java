package org.eclipse.scava.crossflow.tests.matrix;

public class MatrixConfigurationSource extends MatrixConfigurationSourceBase {
	
	protected int numberOfConfigurations = 2;
	
	@Override
	public void produce() {
		for (int i=1;i<numberOfConfigurations+1;i++) {
			MatrixConfiguration matrixConfiguration = new MatrixConfiguration();
			matrixConfiguration.setRows(i);
			matrixConfiguration.setColumns(i);
			matrixConfiguration.setInitialValue(i);
			sendToMatrixConfigurations(matrixConfiguration);
		}
	}
	
	public void setNumberOfConfigurations(int numberOfConfigurations) {
		this.numberOfConfigurations = numberOfConfigurations;
	}
	
}