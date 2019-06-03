package org.eclipse.scava.metricprovider.indexing.documentation.mapping;

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

import org.eclipse.scava.platform.logging.OssmeterLogger;

public final class Mapping {
	
	private static Mapping singleton = new Mapping();
	private String mappingsPath = "/mappings/"; 
	private String dictionnaryName = "mappingsDictionary.txt";
	private HashMap<String,String> mappings;
	protected OssmeterLogger logger;
	
	private Mapping()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("indexing.documentation.mapping");
		String documentType;
		mappings=new HashMap<String,String>();
		try {
			String[] mappingsToRead = loadFile(dictionnaryName).split("\n");
			
			for(String mappingName : mappingsToRead)
			{
				if(mappingName.isEmpty())
					continue;
				documentType=mappingName.replace("_", ".");
				mappings.put(documentType, loadFile(mappingName+".json"));
				logger.info("Mapping for: "+mappingName + " has been loaded.");
			}
		} catch (InputMismatchException | IOException e) {
			logger.error("Error while loading the indexing mappings:", e);
			e.printStackTrace();
		}
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
	
	public static String getMapping(String documentType) {
		
		if(singleton.mappings.containsKey(documentType))
			return singleton.mappings.get(documentType);
		else
		{
			System.err.println("No mapping found for " + documentType);
			return "";
			
		}
	}
}
