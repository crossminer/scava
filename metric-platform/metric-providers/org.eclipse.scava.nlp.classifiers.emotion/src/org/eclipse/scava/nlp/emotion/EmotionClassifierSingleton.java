/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.emotion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipException;

import vasttext.Vasttext;

class EmotionClassifierSingleton
{
	private static EmotionClassifierSingleton singleton = new EmotionClassifierSingleton();
	
	private static Vasttext emotionClassifier;
	
	private EmotionClassifierSingleton()
	{
		emotionClassifier=new Vasttext();
		try {
			loadModel();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkModelFile(Path path) throws FileNotFoundException
	{
		if(!Files.exists(path))
        {
        	throw new FileNotFoundException("The file "+path+" has not been found"); 
        }
	}
	
	private void loadModel() throws ZipException, ClassNotFoundException, IOException
	{
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		if (path.endsWith("target/classes/"))
			path = path.substring(0, path.lastIndexOf("target/classes/"));
		File file= new File(path+"model/Sentic_no_lemma_Vasttext_model.zip");
		checkModelFile(file.toPath());
		emotionClassifier.loadModel(file);
	}
	
	public static EmotionClassifierSingleton getInstance()
	{
		return singleton;
	}
	
	public Vasttext getEmotionClassifier()
	{
		return emotionClassifier;
	}
}
