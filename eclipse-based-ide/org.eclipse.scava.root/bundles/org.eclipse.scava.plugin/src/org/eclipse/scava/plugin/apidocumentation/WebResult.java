package org.eclipse.scava.plugin.apidocumentation;

public class WebResult {
	private final String label;
	private final String address;

	public WebResult(String label, String address) {
		super();
		this.label = label;
		this.address = address;
	}

	public String getLabel() {
		return label;
	}

	public String getAddress() {
		return address;
	}

}
