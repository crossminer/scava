package org.eclipse.crossmeter.contentclassifier.opennlptartarus.libsvm.featuremethods;

import org.eclipse.crossmeter.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class REMethod {

	public static int predict(ClassificationInstance xmlResourceItem) {
		if (xmlResourceItem.getSubject()!=null) {
			if (xmlResourceItem.getSubject().toLowerCase().contains("re: "))
				return 0;	//	"Reply"
			else
				return 1;	//	"Request"
		} else
			return 0;	//	"Reply"
	}
	
}
