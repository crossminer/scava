package org.eclipse.scava.plugin.librarysearch.details;

import java.util.List;

import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.IModel;

import io.swagger.client.model.Artifact;

public interface ILibraryDetailsModel extends IModel {
	Artifact getLibrary();
	
	List<LibraryListInfo> getSimilars();
}
