package org.eclipse.scava.business.model.migration;

import java.util.List;

import org.eclipse.scava.business.model.MavenLibrary;

public class Delta {
	private MavenLibrary libv1;
	private MavenLibrary libv2;
	private List<Object> deltas;
	public MavenLibrary getLibv1() {
		return libv1;
	}
	public void setLibv1(MavenLibrary libv1) {
		this.libv1 = libv1;
	}
	public MavenLibrary getLibv2() {
		return libv2;
	}
	public void setLibv2(MavenLibrary libv2) {
		this.libv2 = libv2;
	}
	public List<Object> getDeltas() {
		return deltas;
	}
	public void setDeltas(List<Object> deltas) {
		this.deltas = deltas;
	}

}
