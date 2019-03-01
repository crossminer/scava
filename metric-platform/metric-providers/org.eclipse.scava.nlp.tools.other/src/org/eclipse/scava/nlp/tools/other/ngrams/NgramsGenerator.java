/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.other.ngrams;

import java.util.ArrayList;
import java.util.List;

public class NgramsGenerator {
	
	/**
	 * This method generates n-grams up to the size indicated by the variable maxNgrams
	 * @param tokens
	 * @param maxNgrams
	 * @return
	 */
	public static List<String> ngramsGenerator(List<String> tokens, int maxNgrams)
	{
		List<String> ngrams = new ArrayList<String>();
    	String ngram;
    	//Generalization for all the ngrams
    	for(int n=2; n<=maxNgrams; n++)
    	{
    		for(int i=0; i<tokens.size()-(n-1); i++)
    		{
    			ngram = "";
    			for(int j=0; j<n; j++)
    			{
    				ngram+=tokens.get(i+j);
    				if(j!=n-1)
    					ngram+=" ";
    			}
    			ngrams.add(ngram);
    		}
    	}
    	return ngrams;
	}
	
	
	/**
	 * This method only generates skip-bigrams (it doesn't generate consecutive word bigrams)
	 * with a maximum gap of maxSkipBigrams
	 * @param tokens
	 * @param maxSkipBigrams
	 * @return
	 */
	public static List<String> skipBigramsGenerator(List<String> tokens, int maxSkipBigrams)
    {
    	List<String> ngrams = new ArrayList<String>();
    	String ngram;
    	//Generalization for all the skip ngrams
   		for(int i=0; i<tokens.size()-1; i++)
		{
			ngram = "";
			//We're skipping the generation of bigrams, we're just generating bigrams with holes of at least 1
			for(int j=i+2; j<i+maxSkipBigrams+2; j++)
			{
				if(j<tokens.size())
				{
					ngram=tokens.get(i)+" "+tokens.get(j);
					ngrams.add(ngram);
				}
			}
			
		}
    	return ngrams;
    }

}
