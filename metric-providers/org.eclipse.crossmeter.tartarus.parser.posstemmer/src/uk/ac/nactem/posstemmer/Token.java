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

import org.apache.commons.lang3.StringUtils;

public class Token {
	
    private String surface;
    private String norm;
    private String pos;
    private boolean negation,
    				elongated,
    				punctuation,
    				letters,
    				allCaps;

	private static String negationSuffix = "_neg";

	private static String elongatedRegex = ".*([a-zA-Z])\\1{2,}.*";
	private static String punctuationRegex = "[!?]+";
	private static String lettersRegex = ".*[a-zA-Z]+.*";

    public Token(String surface, String norm, String pos) {
        this.surface = surface;
        this.norm = norm;
        this.pos = pos;
        negation = false;
		if (surface.matches(elongatedRegex))
			elongated = true;
		else
			elongated = false;
		if (surface.matches(punctuationRegex))
			punctuation = true;
		else
			punctuation = false;
		if (surface.matches(lettersRegex))
			letters = true;
		else
			letters = false;
		if (StringUtils.isAllUpperCase(surface))
			allCaps = true;
		else
			allCaps = false;
    }

    public boolean isAllCaps() {
		return allCaps;
	}

    public boolean isElongated() {
		return elongated;
	}

	public boolean containsPunctuation() {
		return punctuation;
	}

	public boolean containsLetters() {
		return letters;
	}

	public String getSurfaceForm() {
        return surface;
    }

    public String getSurfaceFormNeg() {
    	if (negation)
    		return surface + negationSuffix;
        return surface;
    }

    public String getNormalForm() {
        return norm;
    }

    public String getNormalFormNeg() {
    	if (negation)
    		return norm + negationSuffix;
        return norm;
    }

    public void setNormalForm(String normalForm) {
		norm = normalForm;
	}

	public String getPoS() {
        return pos;
    }

	public void setPoS(String pos) {
		this.pos = pos;
	}
	
	public boolean isNegation() {
		return negation;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
	}

	@Override
    public String toString() {
		String printout = surface + "\t" + norm + "\t" + pos;
		if (negation) return printout + "\t(neg)";
		return printout;
    }

}
