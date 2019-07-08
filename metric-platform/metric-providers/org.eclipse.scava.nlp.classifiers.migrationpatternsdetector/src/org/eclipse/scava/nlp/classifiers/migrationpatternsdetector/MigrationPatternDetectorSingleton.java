package org.eclipse.scava.nlp.classifiers.migrationpatternsdetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.logging.OssmeterLogger;

class MigrationPatternDetectorSingleton {
	
	private static MigrationPatternDetectorSingleton singleton = new MigrationPatternDetectorSingleton();
	private List<List<Pattern>> patternGroups;
	
	private String regexPath="/sources/regex.txt";
	protected OssmeterLogger logger;
	
	private MigrationPatternDetectorSingleton() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.classifiers.migrationpatternsdetector");
		try
		{
			patternGroups = new ArrayList<List<Pattern>>();
			loadFile();
			logger.info("The regex have been sucessfully loaded");
		}
		catch (IOException | InputMismatchException  e) 
		{
			logger.error("Error while loading the regex:", e);
			e.printStackTrace();
		}
	}
	
	private void readFile(BufferedReader lexicon) throws IOException, InputMismatchException
	{
		String line;
		List<Pattern> patternGroup=new ArrayList<Pattern>();
		
		while((line = lexicon.readLine()) != null)
		{
			if (!line.trim().startsWith("#"))
			{
				patternGroup.add(Pattern.compile("(?i)\\b"+line+"\\b"));
			}
			if (line.trim().startsWith("##") && patternGroup.size()>0)
			{
				patternGroups.add(patternGroup);
				patternGroup=new ArrayList<Pattern>();
			}
			
		}
	}
	
	private void loadFile() throws InputMismatchException, IOException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(regexPath);
		//Method to read inside Eclipse
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+regexPath);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+regexPath+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		readFile(br);
		br.close();
		resource.close();
	}
	
	public static MigrationPatternDetectorSingleton getInstance() {
		return singleton;
	}
	
	public List<List<Pattern>> getPatternGroups() {
		return patternGroups;
	}

}
