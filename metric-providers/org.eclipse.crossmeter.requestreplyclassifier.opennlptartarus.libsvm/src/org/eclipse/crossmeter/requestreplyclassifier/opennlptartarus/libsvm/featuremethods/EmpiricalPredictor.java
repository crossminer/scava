package org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.featuremethods;

import org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class EmpiricalPredictor {

	public static int predict(String method, ClassificationInstance xmlResourceItem) {
		if (method.equals("RE")) {
			return REMethod.predict(xmlResourceItem);
		}
		else if (method.equals("CleanQuestionMark")) {
			return CleanQuestionMarkMethod.predict(xmlResourceItem);
		}
		else if (method.equals("CleanQuestionMarkOrWords")) {
			return CleanQuestionMarkOrWordsMethod.predict(xmlResourceItem);
		}
		else if (method.equals("CleanQuestionWords")) {
			return CleanQuestionWordsMethod.predict(xmlResourceItem);
		}
		else if (method.equals("CleanREQuestionMark")) {
			return CleanREQuestionMarkMethod.predict(xmlResourceItem);
		}
		else if (method.equals("CleanREQuestionMarkOrWords")) {
			return CleanREQuestionMarkOrWordsMethod.predict(xmlResourceItem);
		}
		else {
			System.err.println("Invalid empirical feature method: " + method + "!");
			return -1;
		}
	}
	
}
