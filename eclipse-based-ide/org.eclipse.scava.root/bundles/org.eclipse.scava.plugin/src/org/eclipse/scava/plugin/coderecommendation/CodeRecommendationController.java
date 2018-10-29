package org.eclipse.scava.plugin.coderecommendation;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationView.InsertRequestEvent;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

import io.swagger.client.model.ApiCallResult;

public class CodeRecommendationController
		extends AbstractModelViewController<ICodeRecommendationModel, ICodeRecommendationView>
		implements ICodeRecommendationController {

	public CodeRecommendationController(IController parent, ICodeRecommendationModel model,
			ICodeRecommendationView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		List<ApiCallResult> recommendations = getModel().getRecommendations();
		getView().showRecommendations(recommendations);
	}

	@Subscribe
	public void onInsertRequest(InsertRequestEvent e) {
		IDocument document = getModel().getDocument();
		ITextSelection textSelection = getModel().getTextSelection();

		try {
			String lines = String.join("\n", e.getRecommendation().getCodeLines());
			document.replace(textSelection.getOffset(), textSelection.getLength(), lines);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}
