package org.eclipse.scava.index.indexer;

public class MappingStorage {
	float version;
	String mapping;
	
	
	public MappingStorage(String mapping, float version) {
		this.version=version;
		this.mapping=mapping; 
	}
	
	public String getMapping() {
		return mapping;
	}
	
	public float getVersion() {
		return version;
	}
}
