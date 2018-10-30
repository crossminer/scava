package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import java.util.UUID;

import org.eclipse.scava.crossflow.runtime.Mode;

public class CsvSourceSinkCaseMasterWorker {
	
	public static void main(String[] args) throws Exception {
		BaseCase master = new BaseCase();
		master.setName("Master-"+UUID.randomUUID().toString());
		master.run();
	}
	
}
