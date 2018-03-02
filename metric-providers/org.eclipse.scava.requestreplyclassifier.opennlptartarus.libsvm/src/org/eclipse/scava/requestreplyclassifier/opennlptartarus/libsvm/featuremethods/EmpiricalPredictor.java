/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm.featuremethods;

import org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;

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
