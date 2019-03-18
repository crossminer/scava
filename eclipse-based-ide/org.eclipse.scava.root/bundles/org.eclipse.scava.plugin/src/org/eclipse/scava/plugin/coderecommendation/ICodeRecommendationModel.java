package org.eclipse.scava.plugin.coderecommendation;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.mvc.IModel;

import io.swagger.client.model.ApiCallResult;

public interface ICodeRecommendationModel extends IModel {
	IDocument getDocument();

	List<ApiCallResult> getRecommendations();
	
	ITextSelection getTextSelection();
}
