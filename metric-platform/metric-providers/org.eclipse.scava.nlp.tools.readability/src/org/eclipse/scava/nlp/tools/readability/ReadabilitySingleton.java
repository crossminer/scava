package org.eclipse.scava.nlp.tools.readability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

import org.eclipse.scava.platform.logging.OssmeterLogger;

class ReadabilitySingleton {
	
	private static ReadabilitySingleton singleton = new ReadabilitySingleton();
	private Set<String> dictionary;
	protected OssmeterLogger logger;
	private String lexiconPath="lexicon/LexiconDaleChall.txt";

	private ReadabilitySingleton()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.readability");
		try
		{
			loadFile();
			logger.info("Lexicon has been sucessfully loaded");
		}
		catch (IOException | InputMismatchException  e) 
		{
			logger.error("Error while loading the lexicon:", e);
			e.printStackTrace();
		}
	}
	
	private void readLexicon(BufferedReader lexicon) throws IOException, InputMismatchException
	{
		String line;
		dictionary = new HashSet<String>();
		while((line = lexicon.readLine()) != null)
		{
			line=line.trim();
			if (!line.startsWith("#"))
				dictionary.add(line);
		}
	}
	
	private void loadFile() throws InputMismatchException, IOException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(lexiconPath);
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			File file= new File(path+lexiconPath);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+lexiconPath+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		readLexicon(br);
		br.close();
		resource.close();
	}
	
	
	public Set<String> getDictionary() {
		return dictionary;
	}
	
	public static ReadabilitySingleton getInstance() {
		return singleton;
	}
}
