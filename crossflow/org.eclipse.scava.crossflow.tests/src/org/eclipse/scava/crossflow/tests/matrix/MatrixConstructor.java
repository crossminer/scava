package org.eclipse.scava.crossflow.tests.matrix;

public class MatrixConstructor extends MatrixConstructorBase {

	protected int executions = 0;

	@Override
	public Matrix consumeMatrixConfigurations(MatrixConfiguration matrixConfiguration) {

		executions++;

		Matrix matrix = new Matrix();
		matrix.setCorrelationId(matrixConfiguration.getId());

		for (int i = 0; i < matrixConfiguration.getRows(); i++) {
			Row row = new Row();
			matrix.getRows().add(row);
			for (int j = 0; j < matrixConfiguration.getColumns(); j++) {
				row.getCells().add(matrixConfiguration.getInitialValue());
			}
		}

		return matrix;

	}

	public int getExecutions() {
		return executions;
	}

}