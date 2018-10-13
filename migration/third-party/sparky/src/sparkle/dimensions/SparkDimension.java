package sparkle.dimensions;

import java.util.List;

import sparkle.scales.Scale;

public abstract class SparkDimension<T> {
	
	protected Scale<T> scale;
	protected List<T> data;
	
	public SparkDimension(List<T> data) {
		this.data = data;
	}
	
	public abstract T getMinValue();
	
	public abstract T getMaxValue();

	public int indexOf(T obj) {
		return this.data.indexOf(obj);
	}

	public T getRaw(int index) {
		return data.get(index);
	}
	
	public int get(int index) {
		return scale(data.get(index));
	}
	
	public int scale(T obj) {
		return scale.scale(obj);
	}
	
	public int size() {
		return this.data.size();
	}
}