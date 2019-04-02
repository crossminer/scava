/*******************************************************************************
 * Copyright (C) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.preprocessor.normalizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Normalizer
{
	private static SymbolNormalizer symbolNormalizer;
	private static SymbolCleaner symbolCleaner;
	private static SpaceNormalizer spaceNormalizer;
	
	static
	{
		symbolCleaner = new SymbolCleaner();
		symbolNormalizer =  new SymbolNormalizer();
		spaceNormalizer = new SpaceNormalizer();
	}
	
	public static String normalize(String text)
	{
		//The order must be kept always the same otherwise there could be some errors in the regex
		text=symbolCleaner.apply(text);
		text=symbolNormalizer.apply(text);
		text=spaceNormalizer.apply(text);
		return text;
	}
	
	/**
	 * Method that normalizes and cleans a {@code List<String>}.  
	 * 
	 * @param listText A list of Strings that must be normalized.
	 * @return A {@code List<String>} were every {@code String} has been normalized.
	 * 
	 * <b>NOTE:</b> The returned {@code List<String>} might be smaller than the used in the input because empty strings are deleted.
	 */
	public static List<String> normalize(List<String> listText)
	{
		listText.replaceAll(text->normalize(text));
		//We delete those entries that are empty in the next line
		return listText.stream().filter(text->text.isEmpty()==false).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param listPairsText is a list of {@code Map.Entry<T, String>}. The key, of type {@code <T>}, can be of any class and 
	 *  will not affect the normalization process.
	 * 	The value, must be always a {@code String} and it will be the normalized one.
	 * @return A {@code List<Map.Entry<T, String>>} where every value of {@code Map.Entry<T, String>} has been normalized.
	 * 
	 * <b>NOTE:</b> The returned {@code List<Map.Entry<T, String>>} might be smaller than the used in the input because empty strings are deleted.
	 */
	public static <T> List<Map.Entry<T, String>> normalizePair(List<Map.Entry<T, String>> listPairsText)
	{
		listPairsText.forEach(pair->pair.setValue(normalize(pair.getValue())));
		//We delete those entries that are empty in the next line
		return listPairsText.stream().filter(pair->pair.getValue().isEmpty()==false).collect(Collectors.toList());
	}
}
