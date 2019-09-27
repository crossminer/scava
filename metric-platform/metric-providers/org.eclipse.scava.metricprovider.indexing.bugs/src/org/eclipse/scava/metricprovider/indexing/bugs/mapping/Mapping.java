/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.bugs.mapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.index.indexer.MappingStorage;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public final class Mapping {
	
	private static Mapping singleton = new Mapping();
	private String mappingsPath = "/mappings/"; 
	private String dictionnaryName = "mappingsDictionary.txt";
	private HashMap<String,MappingStorage> mappings;
	protected static OssmeterLogger logger;
	
	private Mapping()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("indexing.bugs.mapping");
		String documentType;
		Pattern versionFinder = Pattern.compile("\"mapping_version\"\\s*:\\s*\"([^\"]+)\"");
		mappings=new HashMap<String,MappingStorage>();
		try {
			String[] mappingsToRead = loadFile(dictionnaryName).split("\n");
			
			for(String mappingName : mappingsToRead)
			{
				if(mappingName.isEmpty())
					continue;
				documentType=mappingName.replace("_", ".");
				mappings.put(documentType, loadMapping(mappingName+".json", versionFinder));
				logger.info("Mapping for: "+mappingName + " has been loaded.");
			}
		} catch (InputMismatchException | IOException e) {
			logger.error("Error while loading the indexing mappings:", e);
			e.printStackTrace();
		}
	}
	
	private MappingStorage loadMapping(String fileToRead, Pattern versionFinder) throws InputMismatchException, IOException
	{
		
		String mappingString = loadFile(fileToRead);
		Matcher m = versionFinder.matcher(mappingString);
		if(m.find())
		{
			return new MappingStorage(mappingString, Float.valueOf(m.group(1)));
		}
		logger.error("Invalid mapping, please add a version number");
		return null;
	}
	
	private String loadFile(String fileToRead) throws InputMismatchException, IOException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(mappingsPath+fileToRead);
		//Method to read inside Eclipse
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+mappingsPath+fileToRead);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+mappingsPath+fileToRead+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		String content = "";
		String line;
		while((line = br.readLine()) != null)
		{
				content+=line+"\n";
		}
		br.close();
		resource.close();
		return content;
	}
	
	public static MappingStorage getMapping(String documentType) {
		
		if(singleton.mappings.containsKey(documentType))
			return singleton.mappings.get(documentType);
		else
		{
			logger.error("No mapping found for " + documentType);
			return null;
			
		}
	}
}