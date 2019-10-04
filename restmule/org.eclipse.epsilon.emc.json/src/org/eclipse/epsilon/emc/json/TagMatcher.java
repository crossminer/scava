package org.eclipse.epsilon.emc.json;

public class TagMatcher {
	
	public static boolean matches(JSONElement e, String pattern) {
		if (e == null) return false;
		if (pattern.equals("*")) return true;
		return e.getTag().equals(pattern);
	}
	
}
