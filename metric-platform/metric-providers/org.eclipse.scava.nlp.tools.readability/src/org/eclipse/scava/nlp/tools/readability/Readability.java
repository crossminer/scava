package org.eclipse.scava.nlp.tools.readability;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.core.NLPCoreAnalyzer;

public class Readability {
	
	private static Set<String> dictionary;
	private static Pattern notWords;
	
	static
	{
		dictionary=ReadabilitySingleton.getInstance().getDictionary();
		notWords=Pattern.compile("(\\$|:|,|\\.|``|''|-LRB-|-RRB-|CD|ADD|SYM|XX|LS)");
	}
	
	public static double calculateDaleChall(String text)
	{
		NLPCoreAnalyzer analyzer = new NLPCoreAnalyzer(text);
		List<String> POS = analyzer.getPOS();
		List<String> lemmas = analyzer.getLemmas();
		double sentences = analyzer.getTokenizedSentences().size();
		double words=0;
		double difficultWords=0;
		for(int i=0; i<POS.size(); i++)
		{
			if(!notWords.matcher(POS.get(i)).matches())
			{
				words++;
				if(!dictionary.contains(lemmas.get(i)))
					difficultWords++;
			}
		}

		difficultWords=(difficultWords/words)*100;
		words=(words/sentences);
		
		
		if(difficultWords>5)
			return (0.1579*difficultWords)+(0.0496*words)+3.6365;
		else
			return (0.1579*difficultWords)+(0.0496*words);
	}
	
	public static List<Double> calculateDaleChall(List<String> texts)
	{
		List<Double> results = new ArrayList<Double>(texts.size());
		for(String text : texts)
		{
			results.add(calculateDaleChall(text));
		}
		return results;
	}

}
