package org.eclipse.scava.crossflow.tests.matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixSink extends MatrixSinkBase {
	
	protected List<Matrix> matrices = new ArrayList<>();
	protected boolean printing = false;
	
	@Override
	public void consumeMatrices(Matrix matrix) {
		
		System.out.println("Adding matrix " + matrix.getRows().size());
		
		if (printing) {
			for (Row row : matrix.getRows()) {
				System.out.print("|");
				for (int cell : row.getCells()) {
					System.out.print(cell + "|");
				}
				System.out.println("");
			}
		}
		
		matrices.add(matrix);
		
	}
	
	public List<Matrix> getMatrices() {
		return matrices;
	}
	
	public void setPrinting(boolean printing) {
		this.printing = printing;
	}
	
}