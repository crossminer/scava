/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;

public class ClassifierTest {

    public static void main(String[] args) throws Exception {

    	String newsgroupName  = "newsgroupName";
    	int doc1 = 1001;
    	int doc2 = 1002;
    	int doc3 = 1003;
    	int doc4 = 1004;
    	String sub1 = "Critital alert, this can be very dangerous";
    	String sub2 = "";
    	String text1 = "critical and severe secuerity riks.";
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
        			  "colchicine-treated mice."
        			  ;
    	
    	Classifier classifier = new Classifier();

    	ClassifierMessage classifierMessage1 = new ClassifierMessage();
        classifierMessage1.setNewsgroupName(newsgroupName);
        classifierMessage1.setThreadId(doc1);
        //classifierMessage1.setSubject(sub1);
        classifierMessage1.setText(text1);
        
        FeatureIdCollection featureIdCollection = new FeatureIdCollection();
        featureIdCollection.addUnigram(10);
        featureIdCollection.addUnigram(11);
        featureIdCollection.addUnigram(12);
        featureIdCollection.addUnigram(13);
        featureIdCollection.addUnigram(14);
        featureIdCollection.addUnigram(15);

        classifier.add(classifierMessage1, featureIdCollection);
        
        ClassifierMessage classifierMessage2 = new ClassifierMessage();
        classifierMessage2.setNewsgroupName(newsgroupName);
        classifierMessage2.setThreadId(doc2);
        classifierMessage2.setSubject(sub2);
        classifierMessage2.setText(text2);
        classifier.add(classifierMessage2, featureIdCollection);

        ClassifierMessage classifierMessage3 = new ClassifierMessage();
        classifierMessage3.setNewsgroupName(newsgroupName);
        classifierMessage3.setThreadId(doc3);
        classifierMessage3.setSubject(sub1);
        classifierMessage3.setText(text2);
        classifier.add(classifierMessage3,featureIdCollection);
        
        ClassifierMessage classifierMessage4 = new ClassifierMessage();
        classifierMessage4.setNewsgroupName(newsgroupName);
        classifierMessage4.setThreadId(doc3);
        classifierMessage4.setSubject(sub2);
        classifierMessage4.setText(text1);
        classifier.add(classifierMessage4,featureIdCollection);

        classifier.classify();
        
        System.out.println(classifierMessage1.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classifierMessage1));
        System.out.println(classifierMessage2.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classifierMessage2));
        System.out.println(classifierMessage3.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classifierMessage3));
        System.out.println(classifierMessage4.getComposedId() + 
        		" -> " + classifier.getClassificationResult(classifierMessage4));
        
        System.out.println();

    }

}
