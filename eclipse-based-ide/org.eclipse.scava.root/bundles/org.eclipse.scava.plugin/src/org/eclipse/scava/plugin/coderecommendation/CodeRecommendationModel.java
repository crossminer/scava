package org.eclipse.scava.plugin.coderecommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.ApiCallResult;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class CodeRecommendationModel extends AbstractModel implements ICodeRecommendationModel {
	private final String currentMethodCode;
	private final ITextEditor editor;
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public CodeRecommendationModel(String currentMethodCode, ITextEditor editor) {
		super();
		this.currentMethodCode = currentMethodCode;
		this.editor = editor;
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	@Override
	public List<ApiCallResult> getRecommendations() {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();
		
		Query query = new Query();
		query.setCurrentMethodCode(currentMethodCode);
		
		List<ApiCallResult> results = new ArrayList<>();
		try {
			Recommendation recommendation = recommenderRestController.getApiCallRecommendationUsingPOST(query);
			results = recommendation.getRecommendationItems().stream().map(r -> r.getApiCallRecommendation()).collect(Collectors.toList());
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public IDocument getDocument() {
		IDocumentProvider documentProvider = editor.getDocumentProvider();
		IDocument document = documentProvider.getDocument(editor.getEditorInput());
		
		return document;
	}

	@Override
	public ITextSelection getTextSelection() {
		ISelection selection = editor.getSelectionProvider().getSelection();
		ITextSelection textSelection = (ITextSelection)selection;
		
		return textSelection;
	}
}
