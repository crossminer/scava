/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm;

import org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm.Classifier;

public class ClassifierTest {

    public static void main(String[] args) throws Exception {

    	String newsgroupName  = "newsgroupName";
    	int doc1 = 1001;
    	int doc2 = 1002;
    	int doc3 = 1003;
    	int doc4 = 1004;
    	String sub1 = "the latest of a series of inconclusive Western moves";
    	String sub2 = "re: the latest of a series of inconclusive Western moves";
    	String text1 = "redirection forgot option mailbox";
    	String text2 = "Wot about Fig. 2 and (Fig. 3)? We created a myosinII-responsive FA interactome from proteins " +
        			  "in the expected FA list by color-coding proteins according to MDR magnitude (Supplemental Fig. " +
        			  "S4 and Table 7, http://dir.nhlbi.nih.gov/papers/lctm/focaladhesion/Home/index.html). The " +
        			  "interactome illustrates the full range of MDR values, including proteins exhibiting minor/low " +
        			  "confidence changes. This interactome suggests how myosinII activity may collectively modulate FA " +
        			  "abundance of groups of proteins mediating distinct pathways. The development coincided with a " +
        			  "warning issued in London by the Bosnian Foreign Minister, Irfan Ljubijankic, that the region was " +
        			  "\"dangerously close to a resumption of all-out war.\" He added, \"At the moment we have a diplomatic " +
        			  "vacuum.\"\nIn the latest of a series of inconclusive Western moves to avert a renewed Balkan flareup, " +
        			  "the American envoy, Assistant Secretary of State Richard C. Holbrooke, met with President Franjo " +
        			  "Tudjman at the Presidential Palace in the hills above Zagreb tonight. But the meeting lasted less than " +
        			  "40 minutes and Mr. Holbrooke refused to answer reporters' questions when he left. We worked out a " +
        			  "protocol to study oxidative stress in human peripheral blood lymphocytes by determining their potency " +
        			  "to secrete IFN-gamma, IL-2, IL-4, IL-5, IL-8. The distribution of galanin, neurotensin, met-enkephalin " +
        			  "(mENK), and cholecystokinin (CCK)-immunoreactive cells was determined within the RP3V of " +
        			  "colchicine-treated mice.";
    	
    	Classifier classifier = new Classifier();

    	ClassificationInstance classificationInstance1 = new ClassificationInstance();
        classificationInstance1.setNewsgroupName(newsgroupName);
        classificationInstance1.setArticleNumber(doc1);
        classificationInstance1.setSubject(sub1);
        classificationInstance1.setText(text1);
        classifier.add(classificationInstance1);
        
        ClassificationInstance classificationInstance2 = new ClassificationInstance();
        classificationInstance2.setNewsgroupName(newsgroupName);
        classificationInstance2.setArticleNumber(doc2);
        classificationInstance2.setSubject(sub2);
        classificationInstance2.setText(text2);
        classifier.add(classificationInstance2);

        ClassificationInstance classificationInstance3 = new ClassificationInstance();
        classificationInstance3.setNewsgroupName(newsgroupName);
        classificationInstance3.setArticleNumber(doc3);
        classificationInstance3.setSubject(sub1);
        classificationInstance3.setText(text2);
        classifier.add(classificationInstance3);
        
        ClassificationInstance classificationInstance4 = new ClassificationInstance();
        classificationInstance4.setNewsgroupName(newsgroupName);
        classificationInstance4.setArticleNumber(doc4);
        classificationInstance4.setSubject(sub2);
        classificationInstance4.setText(text1);
        classifier.add(classificationInstance4);

        classifier.classify();
        System.out.println(classificationInstance1.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classificationInstance1));
        System.out.println(classificationInstance2.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classificationInstance2));
        System.out.println(classificationInstance3.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classificationInstance3));
        System.out.println(classificationInstance4.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classificationInstance4));
        
    }

}
