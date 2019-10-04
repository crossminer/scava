package org.eclipse.scava.crossflow.examples.simple.nbody.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.scava.crossflow.examples.simple.nbody.StockCuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodyCuboid.CuboidCoordinates;
import org.eclipse.scava.crossflow.examples.simple.nbody.NBodySimulation.InvalidNumberOfCubesException;

/**
 * A class to create child cuboids from a parent.
 * @author horacio
 *
 */
public class StockCuboids implements Cuboids {
	
	private final CuboidCoordinates parentCuboid;
	

	public StockCuboids(CuboidCoordinates parentCuboid) {
		super();
		this.parentCuboid = parentCuboid;
	}


	@Override
	public Collection<CuboidCoordinates> setupCuboids(final int numCubes) throws InvalidNumberOfCubesException {
		List<CuboidCoordinates> cuboids = new ArrayList<>();
		if (!((numCubes > 0) && ((numCubes & (numCubes - 1)) == 0))) {
			throw new InvalidNumberOfCubesException();
		}
		if (numCubes == 1) {
			cuboids.add(new StockCuboidCoordinates());
		} else {
			// axis 0 = x, 1 = y, 2 = z;
			int xPoints = 0;
			int yPoints = 0;
			int zPoints = 0;
			int axis = 0;
			int numcubes = numCubes;
			while (numcubes > 1) {
				switch (axis) {
				case 0:
					xPoints += 2;
					break;
				case 1:
					yPoints += 2;
					break;
				case 2:
					zPoints += 2;
				default:
					axis = 0;
				}
				numcubes /= 2;
				axis++;
			}
			double[] xCoords = axisSlices(xPoints, parentCuboid.xmin(), parentCuboid.xmax());
			double[] yCoords = axisSlices((yPoints == 0) ? 1 : yPoints, parentCuboid.ymin(), parentCuboid.ymax());
			double[] zCoords = axisSlices((zPoints == 0) ? 1 : zPoints, parentCuboid.zmin(), parentCuboid.zmax());
			for (int i = 0; i < xCoords.length - 1; i++) {
				for (int j = 0; j < yCoords.length - 1; j++) {
					for (int k = 0; k < zCoords.length - 1; k++) {
						cuboids.add(new StockCuboidCoordinates(xCoords[i], yCoords[j], zCoords[k], xCoords[i + 1],
								yCoords[j + 1], zCoords[k + 1]));
					}
				}
			}
		}

		return cuboids;
	}
	
	private double[] axisSlices(double points, double min, double max) {
		double range = max - min;
		double slice = range / points;
		double point = min;
		double[] coords = new double[(int) (points + 1)];
		for (int i = 0; i <= points; i++) {
			coords[i] = point;
			point += slice;
		}
		return coords;
	}

}
