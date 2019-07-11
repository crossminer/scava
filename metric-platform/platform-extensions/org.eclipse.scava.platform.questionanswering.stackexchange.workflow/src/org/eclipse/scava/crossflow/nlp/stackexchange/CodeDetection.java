package org.eclipse.scava.crossflow.nlp.stackexchange;

import org.eclipse.scava.crossflow.nlp.stackexchange.gen.CodeDetectorBase;
import org.eclipse.scava.crossflow.nlp.stackexchange.gen.Post;
import org.eclipse.scava.nlp.classifiers.codedetector.CodeDetector;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPrediction;

public class CodeDetection extends CodeDetectorBase {
	
	@Override
	public Post consumeCodeDetectorQueue(Post post) {
		SingleLabelPrediction prediction =  CodeDetector.predict(post.getPlainText());
		if (prediction.getLabel().equals("__label__Code")) {
			post.setHasCode(true);
		} else {
			post.setHasCode(false);
		}
		return post;
	}

}
