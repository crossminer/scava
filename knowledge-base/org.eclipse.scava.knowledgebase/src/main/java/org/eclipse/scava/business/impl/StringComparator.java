package org.eclipse.scava.business.impl;

import java.util.Comparator;
import java.util.Map;

public class StringComparator implements Comparator<String> {
	Map<String, Float> base;

	public StringComparator(Map<String, Float> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(String a, String b) {
		float va = base.get(a);
		float vb = base.get(b);
		if (va > vb) {
			return -1;
		} else if (va == vb) {
			return a.compareTo(b);
		} else {
			return 1;
		}
	}
}
