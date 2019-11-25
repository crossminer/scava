package org.eclipse.scava.crossflow.runtime.serializer.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializationTestObject {

	String stringProp = null;
	int intProp = 0;
	long longProp = 0;
	boolean booleanProp = true;
	List<SerializationTestObject> listProp = null;
	Map<String, SerializationTestObject> mapProp = null;
	SerializationTestEnum enumProp = null;

	public static SerializationTestObject getPrimitveInstance() {
		SerializationTestObject sto = new SerializationTestObject();
		sto.stringProp = "default";
		sto.intProp = 123;
		sto.longProp = 123L;
		sto.booleanProp = true;
		return sto;
	}

	public static SerializationTestObject getListInstance() {
		SerializationTestObject parent = new SerializationTestObject();
		parent.stringProp = "parent";
		parent.listProp = new ArrayList<SerializationTestObject>();

		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		parent.listProp.add(child1);

		SerializationTestObject child2 = new SerializationTestObject();
		child2.stringProp = "child2";
		parent.listProp.add(child2);

		return parent;
	}

	public static SerializationTestObject getMapInstance() {
		SerializationTestObject parent = new SerializationTestObject();
		parent.stringProp = "parent";
		parent.mapProp = new HashMap<String, SerializationTestObject>();

		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		parent.mapProp.put("child1_key", child1);

		SerializationTestObject child2 = new SerializationTestObject();
		child2.stringProp = "child2";
		parent.mapProp.put("child2_key", child2);

		return parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (booleanProp ? 1231 : 1237);
		result = prime * result + ((enumProp == null) ? 0 : enumProp.hashCode());
		result = prime * result + intProp;
		result = prime * result + ((listProp == null) ? 0 : listProp.hashCode());
		result = prime * result + (int) (longProp ^ (longProp >>> 32));
		result = prime * result + ((mapProp == null) ? 0 : mapProp.hashCode());
		result = prime * result + ((stringProp == null) ? 0 : stringProp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerializationTestObject other = (SerializationTestObject) obj;
		if (booleanProp != other.booleanProp)
			return false;
		if (enumProp != other.enumProp)
			return false;
		if (intProp != other.intProp)
			return false;
		if (listProp == null) {
			if (other.listProp != null)
				return false;
		} else if (!listProp.equals(other.listProp))
			return false;
		if (longProp != other.longProp)
			return false;
		if (mapProp == null) {
			if (other.mapProp != null)
				return false;
		} else if (!mapProp.equals(other.mapProp))
			return false;
		if (stringProp == null) {
			if (other.stringProp != null)
				return false;
		} else if (!stringProp.equals(other.stringProp))
			return false;
		return true;
	}

}
