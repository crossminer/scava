/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.requestreplydetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.eclipse.scava.platform.logging.OssmeterLogger;

import vasttext.Vasttext;

class RequestReplyClassifierSingleton
{
	private static RequestReplyClassifierSingleton singleton = new RequestReplyClassifierSingleton();
	protected OssmeterLogger logger;
	private Vasttext requestReplyClassifier;
	private String modelPath="model/VastText_Code_no_lemma_model.zip";
	
	private RequestReplyClassifierSingleton()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.classifiers.requestreplydetector");
		requestReplyClassifier = new Vasttext();
		try
		{
			loadModel();
			logger.info("Model has been sucessfully loaded");
		}
		catch (ClassNotFoundException | IOException e)
		{
			logger.error("Error while loading the model:", e);
			e.printStackTrace();
		}
		
	}
	
	private void loadModel() throws ClassNotFoundException, IOException
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(modelPath);
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+modelPath);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+modelPath+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		requestReplyClassifier.loadModel(resource);
		resource.close();
	}
	
	public static RequestReplyClassifierSingleton getInstance()
	{
		return singleton;
	}
	
	public Vasttext getRequestReplyClassifier()
	{
		return requestReplyClassifier;
	}

}
