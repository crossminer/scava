package sparkle.dimensions;

import java.util.Collections;
import java.util.List;

import sparkle.scales.LinearScale;

public class LinearDimension extends SparkDimension<Double>{
	
	public LinearDimension(List<Double> data, int maxRange, int minRange) {
		super(data);
		scale = new LinearScale(getMaxValue(), getMinValue(), maxRange, minRange);
	}

	@Override
	public Double getMinValue() {
		return Collections.min(data);
	}

	@Override
	public Double getMaxValue() {
		return Collections.max(data);
	}
}