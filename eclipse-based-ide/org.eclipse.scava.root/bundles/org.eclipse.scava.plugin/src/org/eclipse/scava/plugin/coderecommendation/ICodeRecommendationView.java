package org.eclipse.scava.plugin.coderecommendation;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.ApiCallResult;

public interface ICodeRecommendationView extends IView {
	void showRecommendations(List<ApiCallResult> recommendations);
	
	static class InsertRequestEvent extends AbstractEvent<ICodeRecommendationView> {
		private final ApiCallResult recommendation;

		public InsertRequestEvent(ICodeRecommendationView sender, ApiCallResult recommendation) {
			super(sender);
			this.recommendation = recommendation;
		}

		public ApiCallResult getRecommendation() {
			return recommendation;
		}
	}

	static class Close extends ViewCloseEvent<ICodeRecommendationView> {

		public Close(ICodeRecommendationView view) {
			super(view);
		}

	}
}
