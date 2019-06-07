package org.eclipse.scava.nlp.classifiers.documentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.logging.OssmeterLogger;

class DocumentationClassifierSingleton {

	private static DocumentationClassifierSingleton singleton = new DocumentationClassifierSingleton();
	private String regexPath = "/regex/"; 
	private String indexName = "RegexFileIndex.txt";
	protected OssmeterLogger logger;
	private HashMap<String,List<Pattern>> classesRegex;
	
	private DocumentationClassifierSingleton() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.classifiers.documentation");
		try {
			String[] filesToRead = loadFile(indexName).split("\n");
			classesRegex = new HashMap<String,List<Pattern>>(filesToRead.length);
			for(String fileName : filesToRead)
			{
				if(fileName.isEmpty())
					continue;
				List<Pattern> patterns = new ArrayList<Pattern>(1);
				for(String regex : loadFile(fileName+".txt").split("\n"))
				{
					patterns.add(Pattern.compile(regex));
				}
				classesRegex.put(fileName, patterns);
				logger.info("Regex for "+fileName + " has been loaded.");
			}
		} catch (InputMismatchException | IOException e) {
			logger.error("Error while loading the indexing mappings:", e);
			e.printStackTrace();
		}
	}
	
	private String loadFile(String fileToRead) throws InputMismatchException, IOException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(regexPath+fileToRead);
		//Method to read inside Eclipse
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+regexPath+fileToRead);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+regexPath+fileToRead+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		String content = "";
		String line;
		while((line = br.readLine()) != null)
		{
			if(line.startsWith("#"))
				continue;
			content+=line+"\n";
		}
		br.close();
		resource.close();
		return content;
	}
	
	public static DocumentationClassifierSingleton getInstance()
	{
		return singleton;
	}
	
	public HashMap<String, List<Pattern>> getClassesRegex() {
		return classesRegex;
	}
}
