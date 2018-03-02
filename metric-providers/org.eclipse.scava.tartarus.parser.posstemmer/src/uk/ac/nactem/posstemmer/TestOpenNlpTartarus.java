/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package uk.ac.nactem.posstemmer;

import java.util.List;


public class TestOpenNlpTartarus {
	
	private static String inputA = 
			"Can anyone help me dig through OpenNLP's horrible documentation? " +
			"In clinical practice, doctors personally assess patients in order to " +
			"diagnose, treat, and prevent disease using clinical judgment. The " +
			"doctor-patient relationship typically begins an interaction with an " +
			"examination of the patient's medical history and medical record, followed " +
			"by a medical interview[4] and a physical examination. Basic diagnostic " +
			"medical devices (e.g. stethoscope, tongue depressor) are typically used. " +
			"After examination for signs and interviewing for symptoms, the doctor may " +
			"order medical tests (e.g. blood tests), take a biopsy, or prescribe " +
			"pharmaceutical drugs or other therapies. Differential diagnosis methods " +
			"help to rule out conditions based on the information provided. During the " +
			"encounter, properly informing the patient of all relevant facts is an " +
			"important part of the relationship and the development of trust. The " +
			"medical encounter is then documented in the medical record, which is a " +
			"legal document in many jurisdictions.[5] Follow-ups may be shorter but " +
			"follow the same general procedure. ";
	private static String inputB = 
            "We worked out a protocol to study oxidative stress in human peripheral " +
            "blood lymphocytes by determining their potency to secrete IFN-gamma, IL-2, " +
            "IL-4, IL-5, IL-8. " + 
            "The distribution of galanin, neurotensin, met-enkephalin (mENK), and " +
            "cholecystokinin (CCK)-immunoreactive cells was determined within the RP3V " +
            "of colchicine-treated mice.";
	
	public static void main(String[] args) {

		OpenNlpTartarusSingleton tartarusA = OpenNlpTartarusSingleton.getInstance();
		List<List<Token>> tokenSentences = tartarusA.getTagger().tag(inputA);

		for (List<Token> tokens: tokenSentences) {
			for (Token token: tokens)
				System.out.println(token.toString());
			System.out.println();
		}

		OpenNlpTartarusSingleton tartarusB = OpenNlpTartarusSingleton.getInstance();
		tokenSentences = tartarusB.getTagger().tag(inputB);

		for (List<Token> tokens: tokenSentences)
			for (Token token: tokens)
				System.out.println(token.toString());
			System.out.println();

	}

}
