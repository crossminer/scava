package org.eclipse.scava.nlp.tools.rake;

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

public class RAKESingleton {
	
	private static RAKESingleton singleton = new RAKESingleton();
	
	protected OssmeterLogger logger;
	private String resourcesPath="/resources/";
	private List<String> stopwords;
	private List<Pattern> stopwordsPatterns;
	private List<String> semiStopwords;
	private List<Pattern> semiStopwordsPatterns;

	private RAKESingleton() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.rake");
		try
		{
			stopwords = new ArrayList<String>();
			loadFile("stopwords.txt", stopwords);
			stopwordsPatterns = new ArrayList<Pattern>(stopwords.size());
			
			for(String stopword : stopwords)
				stopwordsPatterns.add(Pattern.compile("\\b"+stopword+"\\b"));
			
			semiStopwords = new ArrayList<String>();
			loadFile("semiStopwords.txt", semiStopwords);
			semiStopwordsPatterns = new ArrayList<Pattern>(semiStopwords.size()*4);
			
			for(String stopword : semiStopwords)
			{
				semiStopwordsPatterns.add(Pattern.compile("S "+stopword+"\\b"));
				semiStopwordsPatterns.add(Pattern.compile("\\b"+stopword+" S"));
				semiStopwordsPatterns.add(Pattern.compile("^ ?"+stopword+" "));
				semiStopwordsPatterns.add(Pattern.compile(stopword+" ?$"));
			}
			logger.info("The stopwords have been sucessfully loaded");
		}
		catch (IOException | InputMismatchException  e) 
		{
			logger.error("Error while loading the stopwords:", e);
			e.printStackTrace();
		}	
	}
	
	private void loadFile(String fileName, List<String> list) throws InputMismatchException, IOException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(resourcesPath+fileName);
		//Method to read inside Eclipse
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+resourcesPath+fileName);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+resourcesPath+fileName+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		readFile(br, list);
		br.close();
		resource.close();
	}
	
	private void readFile(BufferedReader lexicon, List<String> list) throws IOException, InputMismatchException
	{
		String line;
		while((line = lexicon.readLine()) != null)
		{
			if (!line.trim().startsWith("#"))
				list.add(line);
		}
	}
	
	public static RAKESingleton getInstance()
	{
		return singleton;
	}
	
	public List<String> getStopwords() {
		return stopwords;
	}
	
	public List<String> getSemiStopwords() {
		return semiStopwords;
	}
	
	public List<Pattern> getStopwordsPatterns() {
		return stopwordsPatterns;
	}
	
	public List<Pattern> getSemiStopwordsPatterns() {
		return semiStopwordsPatterns;
	}
	
}
