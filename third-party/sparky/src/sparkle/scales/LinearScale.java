package sparkle.scales;
public class LinearScale extends Scale<Double> {
		
	public LinearScale(double maxDomain, double minDomain, double maxRange, double minRange) {
		super(maxDomain, minDomain, maxRange, minRange);
	}
	
	@Override
	public int scale(Double value) {
		return (int)(((value-minDomain)/(maxDomain-minDomain)) * (maxRange-minRange) + minRange);
	}
}
