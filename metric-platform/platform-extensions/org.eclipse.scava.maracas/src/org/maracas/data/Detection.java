package org.maracas.data;

import io.usethesource.vallang.IConstructor;
import io.usethesource.vallang.IReal;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.ITuple;
import io.usethesource.vallang.IValue;

public class Detection {
	public enum Type {
		ACCESS_MODIFIER, FINAL_MODIFIER, STATIC_MODIFIER, ABSTRACT_MODIFIER, DEPRECATED, RENAMED, MOVED, REMOVED,
		PARAMS_LIST, RETURN_TYPE, TYPE, EXTENDS, IMPLEMENTS
	}

	private final String clientLocation;
	private final String oldLibraryLocation;
	private final String newLibraryLocation;
	private final Type type;
	private final double score;

	public Detection(String client, String oldLibrary, String newLibrary, Type type, double score) {
		this.clientLocation = client;
		this.oldLibraryLocation = oldLibrary;
		this.newLibraryLocation = newLibrary;
		this.type = type;
		this.score = score;
	}

	public static Detection fromRascalDetection(IConstructor detection) {
		// detection(loc elem, loc used, tuple[&T from, &T to, real conf, str m], BCTyp
		// typ)
		String client = ((ISourceLocation) detection.get(0)).toString();
		String oldLibrary = ((ISourceLocation) detection.get(1)).toString();
		String newLibrary = "";
		ITuple mapping = (ITuple) detection.get(2);
		IValue to = mapping.get(1);
		double score = ((IReal) mapping.get(2)).doubleValue();
		IConstructor bcType = (IConstructor) detection.get(3);

		Type type;
		switch (bcType.getName()) {
			case "accessModifiers":
				type = Type.ACCESS_MODIFIER;
				newLibrary = oldLibrary;
				break;
			case "finalModifiers":
				type = Type.FINAL_MODIFIER;
				newLibrary = oldLibrary;
				break;
			case "staticModifiers":
				type = Type.STATIC_MODIFIER;
				newLibrary = oldLibrary;
				break;
			case "abstractModifiers":
				type = Type.ABSTRACT_MODIFIER;
				newLibrary = oldLibrary;
				break;
			case "deprecated":
				type = Type.DEPRECATED;
				newLibrary = ((ISourceLocation) to).toString();
				break;
			case "renamed":
				type = Type.RENAMED;
				newLibrary = ((ISourceLocation) to).toString();
				break;
			case "moved":
				type = Type.MOVED;
				newLibrary = ((ISourceLocation) to).toString();
				break;
			case "removed":
				type = Type.REMOVED;
				break;
			case "paramLists":
				type = Type.PARAMS_LIST;
				// Compute it
				break;
			case "types":
				type = Type.TYPE;
				newLibrary = oldLibrary;
				break;
			case "extends":
				type = Type.EXTENDS;
				newLibrary = oldLibrary;
				break;
			case "implements":
				type = Type.IMPLEMENTS;
				newLibrary = oldLibrary;
				break;
			default:
				throw new RuntimeException("Unexpected BCType");
		}

		return new Detection(client, oldLibrary, newLibrary, type, score);
	}

	public String getClientLocation() {
		return clientLocation;
	}

	public String getOldLibraryLocation() {
		return oldLibraryLocation;
	}

	public String getNewLibraryLocation() {
		return newLibraryLocation;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s uses %s, replaced with %s [%.2f]", type, clientLocation, oldLibraryLocation,
				newLibraryLocation, score);
	}
}
