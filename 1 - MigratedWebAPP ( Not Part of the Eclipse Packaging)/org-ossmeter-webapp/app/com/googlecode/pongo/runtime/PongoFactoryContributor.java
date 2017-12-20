package com.googlecode.pongo.runtime;

public interface PongoFactoryContributor {
	
	public boolean canCreate(String className);
	
	public Pongo create(String className) throws Exception;
	
}
