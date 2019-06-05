package org.eclipse.scava.nlp.tools.preprocessor.fileparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.InputMismatchException;

import org.apache.tika.parser.AutoDetectParser;
import org.eclipse.scava.platform.logging.OssmeterLogger;

class FileParserSingleton {

	private static FileParserSingleton singleton = new FileParserSingleton();
	private OssmeterLogger logger;
	private String supportedFilesListPath="/extraData/supportedFilesToText.txt";
	private AutoDetectParser parser;
	private HashMap<String,String> supportedFiles = new HashMap<String,String>();
	private HashMap<String,String> contentHandlerType = new HashMap<String,String>();
	
	private FileParserSingleton()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.preprocessor.fileparser");
		BufferedReader fileList;
		try
		{
			fileList = loadFile();
			readSupportedFilesList(fileList);
			logger.info("List of supported files has been sucessfully loaded");
			parser = new AutoDetectParser();
		}
		catch (IOException  e) 
		{
			logger.error("Error while loading the List of supported files:", e);
			e.printStackTrace();
		}	
	}
	
	private void readSupportedFilesList(BufferedReader fileList) throws IOException
	{
		String line;
		String[] entry;
		while((line = fileList.readLine()) != null)
		{
			if (!line.trim().startsWith("#"))
			{
				entry=line.split("\\t");
				if(entry.length!=3)
				{
					throw new InputMismatchException("The supported file List "+ supportedFilesListPath+" has errors in its format in line: "+line); 
				}
				supportedFiles.put(entry[1], entry[0]);
				contentHandlerType.put(entry[1], entry[2]);
			}
		}
	}
	
	private BufferedReader loadFile() throws UnsupportedEncodingException, FileNotFoundException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(supportedFilesListPath);
		//Method to read inside Eclipse
		if(resource==null)
		{
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+supportedFilesListPath);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+supportedFilesListPath+" has not been found");
			else
				resource=new FileInputStream(file);
		}
		return new BufferedReader(new InputStreamReader(resource, "UTF-8"));
	}

	public static FileParserSingleton getInstance() {
		return singleton;
	}

	public AutoDetectParser getParser() {
		return parser;
	}

	public HashMap<String, String> getSupportedFiles() {
		return supportedFiles;
	}
	
	public HashMap<String, String> getContentHandlerType() {
		return contentHandlerType;
	}
	
	
}
