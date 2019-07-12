package org.maracas.test.data;

public class AST3 {
	public boolean lessThan(int i, int j, boolean include) {
		boolean resp;
		if (i > j) {
			resp = true;
		}
		else if (i == j) {
			resp = include;
		}
		else {
			resp = false;
		}
		return resp;
	}
}
