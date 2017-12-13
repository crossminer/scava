package org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.featuremethods;

import org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class CleanQuestionMarkMethod {

	public static int predict(ClassificationInstance xmlResourceItem) {
		if (xmlResourceItem.getCleanText().contains("?"))
			return 1;	//	"Request"
		else
			return 0;	//	"Reply"
	}

}
