package org.eclipse.scava.nlp.tool.rake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.scava.nlp.tools.preprocessor.normalizer.Normalizer;

public class RAKE {
	
	private static Pattern sentenceDelimiters;
	private static Pattern spacingBorders;
	private static Pattern spacing;
	private static List<Pattern> stopwordsPattern=null;
	private static List<Pattern> semiStopwordsPattern=null;
	private static List<String> semiStopwords=null;
	
	static {
		sentenceDelimiters= Pattern.compile("[\\.?;:,!\"()\\[\\]{}\n+=*]");
		spacingBorders=Pattern.compile("(^ +| +$)");
		spacing=Pattern.compile("\\h\\h+");
		
		RAKESingleton singleton = RAKESingleton.getInstance();
		
		stopwordsPattern=singleton.getStopwordsPatterns();
		semiStopwordsPattern=singleton.getSemiStopwordsPatterns();
		semiStopwords=singleton.getSemiStopwords();
	}
	
	
	private static Map<String, Double> extractKeywords(String text)
	{
		text=Normalizer.normalize(text);
		text=text.toLowerCase(Locale.ENGLISH);
		List<String> sentences = Arrays.asList(sentenceDelimiters.split(text));
		
		Map<String, List<String>> candidates = Collections.synchronizedMap(new HashMap<String, List<String>>());
		
		Map<String, Double> deg = Collections.synchronizedMap(new HashMap<String,Double>());
		Map<String, Double> freq = Collections.synchronizedMap(new HashMap<String,Double>());
		

		sentences.parallelStream().forEach(s->{
			for(Pattern stopword : stopwordsPattern)
				s=stopword.matcher(s).replaceAll("S");
			s=spacing.matcher(s).replaceAll(" ");
			
			for(Pattern stopword : semiStopwordsPattern)
				s=stopword.matcher(s).replaceAll("S");
			
			String[] candidatesArray=s.split("S");
			for(String candidate : candidatesArray)
			{
				candidate=spacingBorders.matcher(candidate).replaceAll("");
				if(candidate.isEmpty())
					continue;
				List<String> words = new ArrayList<String>(Arrays.asList(candidate.split("\\h+")));
				
				words.removeAll(semiStopwords);
				candidates.putIfAbsent(candidate, words);
								
				for(String word : words)
				{
					freq.compute(word, (k,v)-> v==null ? 1 : v+1);
					if(words.size()>1)
						deg.compute(word, (k,v)-> v==null ? words.size() : v+words.size());
					else
						deg.compute(word, (k,v)-> v==null ? 1 : v+1);
				}
			}
				
		});
		
		Map<String, Double> candidatesWeighted = Collections.synchronizedMap(new HashMap<String, Double>(candidates.size()));
		Map<String, Double> weights = Collections.synchronizedMap(new HashMap<String,Double>(deg.size()));
		
		deg.entrySet().parallelStream().forEach(w->{
			weights.put(w.getKey(), w.getValue()/freq.get(w.getKey()));
		});
		
		
		candidates.entrySet().parallelStream().forEach(c -> {
			Double weight=0.0;
			for(String word : c.getValue())
				weight+=weights.get(word);
			candidatesWeighted.put(c.getKey(), weight);
		});
		
		
		
		
		Map<String, Double> keywords = candidatesWeighted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
												.limit((int) Math.round(deg.size()/3.0))
												.collect(Collectors.toMap(
												          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		
		return keywords;
	}
}
