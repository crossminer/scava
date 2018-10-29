package org.eclipse.scava.plugin.librarysearch.details;

import java.util.List;

import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

import io.swagger.client.model.Artifact;

public class LibraryDetailsModel extends AbstractModel implements ILibraryDetailsModel {
	private final Artifact library;
	private final List<LibraryListInfo> similars;

	public LibraryDetailsModel(Artifact library, List<LibraryListInfo> similars) {
		super();
		this.library = library;
		this.similars = similars;
	}

	@Override
	public Artifact getLibrary() {
		return library;
	}

	@Override
	public List<LibraryListInfo> getSimilars() {
		return similars;
	}

}
