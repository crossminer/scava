package org.eclipse.scava.crossflow.restmule.client.github.test.query;

public interface INumericValue<T,O> {

	public static final String BETWEEN = "..";
	public static final String GT = ">";
	public static final String GEQ = ">=";
	public static final String EQ = "=";
	public static final String LEQ = "<=";
	public static final String LT = "<";
	
	O between(T start, T end);
	O gt(T val);
	O geq(T val);
	O eq(T val);
	O leq(T val);
	O lt(T val);
}
