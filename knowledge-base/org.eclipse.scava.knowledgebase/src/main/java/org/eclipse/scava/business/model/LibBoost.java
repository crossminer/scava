package org.eclipse.scava.business.model;

import org.springframework.data.annotation.Id;

public class LibBoost {
	
	public LibBoost() {
		super();
	}
	
	public LibBoost(String name) {
		this.name = name;
	}
	@Id
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
