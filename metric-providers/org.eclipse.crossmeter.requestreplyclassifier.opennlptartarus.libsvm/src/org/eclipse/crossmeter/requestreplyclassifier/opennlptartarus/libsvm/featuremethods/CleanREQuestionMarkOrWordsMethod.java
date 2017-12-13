package org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.featuremethods;

import org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class CleanREQuestionMarkOrWordsMethod {
	
	public static int predict(ClassificationInstance xmlResourceItem) {
		return combine(
					REMethod.predict(xmlResourceItem), 
					CleanQuestionMarkMethod.predict(xmlResourceItem), 
					CleanQuestionWordsMethod.predict(xmlResourceItem)
				);
	}
	
	private static int combine(int rePrediction, int cleanQmPrediction, int cleanQwPrediction) {
		if (rePrediction==1)	//	"Request"
			return rePrediction;
		if (cleanQmPrediction == 1)		//	"Request"
			return cleanQmPrediction;
		return cleanQwPrediction;
	}

}

