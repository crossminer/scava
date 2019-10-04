package org.eclipse.scava.crossflow.web;

import java.util.HashMap;

import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Workflow;

public class ExperimentRegistry {
	
	// <experimentId, Experiment | .getJar()>
	protected static HashMap<String, Experiment> experiments = new HashMap<>();
	
	protected static HashMap<String, Workflow> workflows = new HashMap<>();
	
	protected static HashMap<String, Cache> caches = new HashMap<>();

	
	/**
	 * 
	 * @param experimentId
	 * @return null if not there
	 */
	public static Experiment getExperiment(String experimentId) {
		return experiments.get(experimentId);
	}
	
	/**
	 * 
	 * @param experiment
	 * @return true if added successfully and false otherwise (same experiment is currently deployed)
	 */
	public static void addExperiment(Experiment experiment) {
		experiments.put(experiment.getId(), experiment);
	}
	
	public static Workflow getWorkflow(String experimentId) {
		return workflows.get(experimentId);
	}
	
	public static void addWorkflow(Workflow workflow, String experimentId) {
		System.out.println("Adding workflow with experiment id = " + experimentId);
		Workflow previousWorkflow = workflows.get(experimentId);
		
		if ( previousWorkflow == null ) {
			workflows.put(experimentId, workflow);		
			if ( workflow.getCache()!=null ) {
				caches.put(experimentId, workflow.getCache());
			}
		} else {
			System.err.println("Unable to add workflow (already exists in registry).");
		}
	}//addWorkflow

	public static void removeWorkflow(String experimentId) {
		System.out.println("Removing workflow with experiment id = " + experimentId);
		Workflow previousWorkflow = workflows.get(experimentId);
		
		if ( previousWorkflow != null ) {
			workflows.remove(experimentId);
		} else {
			System.err.println("Unable to remove workflow (does not exist in registry).");
		}
	}// removeWorkflow
	
	public static Cache getCache(String experimentId) {
		return caches.get(experimentId);
	}// getCache
	
}
