package org.eclipse.scava.crossflow.runtime.serializer.tests;

import java.util.List;
import java.util.Map;

public class DummyObject {

	String stringProp = null;
	int intProp = 0;
	long longProp = 0;
	boolean booleanProp = true;
	List<DummyObject> listProp = null;
	Map<String, DummyObject> mapProp = null;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (booleanProp ? 1231 : 1237);
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
		DummyObject other = (DummyObject) obj;
		if (booleanProp != other.booleanProp)
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
