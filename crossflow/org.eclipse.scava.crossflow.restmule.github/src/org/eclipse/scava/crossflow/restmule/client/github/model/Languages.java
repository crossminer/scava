package org.eclipse.scava.crossflow.restmule.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Languages {

	public Languages(){}

	@Override
	public String toString() {
		return "Languages [ "
			+ "]"; 
	}	
}	
