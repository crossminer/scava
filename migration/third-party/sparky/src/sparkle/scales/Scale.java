package sparkle.scales;

public abstract class Scale<T> {
	
	protected T maxDomain;
	protected T minDomain;
	protected double maxRange;
	protected double minRange;
	
	/**
	 * 
	 * @param maxDomain max value of data to be plotted
	 * @param minDomain min value of data to be plotted
	 * @param maxRange max pixel point of canvas 
	 * @param minRange min pixel point of canvas
	 */
	public Scale(T maxDomain, T minDomain, double maxRange, double minRange) {
		this.maxDomain = maxDomain;
		this.minDomain = minDomain;
		this.maxRange = maxRange;
		this.minRange = minRange;
	}

	public abstract int scale(T value);

}