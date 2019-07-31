package org.maracas.test.data;

public class AST5 {
	public boolean lessThan(int i, int j) {
		boolean resp = false;
		if (i > j) {
			resp = true;
		}
		if (i <= j) {
			resp = false;
		}
		return resp;
	}
}
