package sparkle.dimensions;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import sparkle.scales.DateScale;

public class DateDimension extends SparkDimension<Date>{
	
	public DateDimension(List<Date> data, int maxRange, int minRange) {
		super(data);
		scale = new DateScale(getMaxValue(), getMinValue(), maxRange, minRange);
	}

	@Override
	public Date getMinValue() {
		return Collections.min(data);
	}

	@Override
	public Date getMaxValue() {
		return Collections.max(data);
	}
}