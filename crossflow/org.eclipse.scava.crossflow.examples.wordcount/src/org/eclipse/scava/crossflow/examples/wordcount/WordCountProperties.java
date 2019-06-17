/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.wordcount;

/**
 * Shared application properties.
 * 
 * @author Patrick Neubauer
 *
 */
public class WordCountProperties {
	
	/**
	 * Shared instance identifier among master and workers.
	 */
	public static final String INSTANCE_ID = "Word Count Example";
	
	/**
	 * Minimum frequency of a word to be included in the output.
	 */
	public static final int MIN_WORD_FREQUENCY = 100;

}// WordCountProperties