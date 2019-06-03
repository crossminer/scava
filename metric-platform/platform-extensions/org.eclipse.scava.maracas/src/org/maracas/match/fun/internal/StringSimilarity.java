package org.maracas.match.fun.internal;

import org.rascalmpl.interpreter.IEvaluatorContext;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import io.usethesource.vallang.IBool;
import io.usethesource.vallang.IReal;
import io.usethesource.vallang.IString;
import io.usethesource.vallang.IValueFactory;

public class StringSimilarity {

	private final IValueFactory factory;
	
	public StringSimilarity(IValueFactory factory) {
		this.factory = factory;
	}
	
	public IBool codeIsSimilar(IString snippet1, IString snippet2, IReal threshold, IEvaluatorContext eval) {
		NormalizedLevenshtein levenshtein = new NormalizedLevenshtein();
		double distance = levenshtein.distance(snippet1.getValue(), snippet2.getValue());
		return factory.bool(distance <= (1 - threshold.doubleValue()));
	}
	
	public IReal levenshteinSimilarity(IString snippet1, IString snippet2, IEvaluatorContext eval) {
		NormalizedLevenshtein levenshtein = new NormalizedLevenshtein();
		double distance = levenshtein.distance(snippet1.getValue(), snippet2.getValue());
		return factory.real(1 - distance);
	}
}
