/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.datasets.file;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDataSet
{
	protected Path textFilePath;
	protected Path extraFilePath;
	
	protected boolean multilabel;
	protected boolean labelled;
	protected boolean numericFeatures;
	
	private int numericFeaturesSize=0;
	
	protected FileDataSet(Path textFilePath, Path extraFilePath)
	{
		if(textFilePath==null)
			throw new UnsupportedOperationException("A file containing the text is mandatory");
		this.textFilePath=textFilePath;
		this.extraFilePath=extraFilePath;
	}
	
	protected FileDataSet(Path textFilePath)
	{
		if(textFilePath==null)
			throw new UnsupportedOperationException("A file containing the text is mandatory");
		this.textFilePath=textFilePath;
		this.extraFilePath=null;
	}
	
	private BufferedReader loadFile(Path filePath) throws FileNotFoundException
	{
		if(!Files.exists(filePath))
        {
			throw new FileNotFoundException("The file "+ filePath +" wasn't found");
        }
		return new BufferedReader(new FileReader(filePath.toString()));
	}
	
	protected int checkFiles(Path filePath, boolean labelsChecking) throws IOException
	{
		BufferedReader file = loadFile(filePath);
		String line;
		int labelsResults=-1;
		int numeriFeaturesResult=-1;
		int numericFeaturesPerEntry=-1;
		int lineCounter=1;
		boolean multiLabelResult=false;
		while((line = file.readLine()) != null)
		{
			if(labelsChecking)
			{
				labelsResults=checkLabels(line, isMultiLabel());
				if(labelsResults==-1)
					throw new EOFException("The line " + lineCounter +" is empty.");
				if(labelsResults==0)
					throw new EOFException("The line " + lineCounter +" is not labelled.");
				if(labelsResults==2)
					multiLabelResult=true;
			}
			else
			{
				numeriFeaturesResult=checkNumericFeatures(line);
				if(numeriFeaturesResult==-1)
					throw new EOFException("The line " + lineCounter +" is empty.");
				else if(numericFeaturesPerEntry==-1)
					numericFeaturesSize=numeriFeaturesResult;
				else if(numericFeaturesSize!=numeriFeaturesResult)
					throw new EOFException("The line " + lineCounter +" was expected to have "+ numericFeaturesSize + " numeric features but found " + numeriFeaturesResult +".");
			}
			lineCounter++;	
		}
		if(numeriFeaturesResult==-1  && !labelsChecking)
			throw new EOFException("The file " + filePath +" is empty.");
		if(labelsResults==-1  && labelsChecking)
			throw new EOFException("The file " + filePath +" is empty.");
		if(multiLabelResult != isMultiLabel() && labelsChecking)
			System.err.println("WARNING: The data set is single-label, but declared as multi-label. This might affect the training.");
		return lineCounter-1;
	}
	
	private int checkNumericFeatures(String line)
	{
		if(line.isEmpty())
			return -1;
		String[] features =line.split(" ");
		return features.length;
	}
	
	private int checkLabels(String line, boolean multiLabel)
	{
		if(line.isEmpty())
			return -1;
		String[] tempLine =line.split("\t");
		if(tempLine.length!=2)
			return 0;
		else if(multiLabel)
		{
			tempLine = tempLine[0].split(" ");
			if(tempLine.length>1)
				return 2;
			else
				return 1;
		}
		else
			return 1;
	}
	
	public int numericFeaturesSize()
	{
		return numericFeaturesSize;
	}
	
	public boolean hasNumericFeatures()
	{
		return numericFeatures;
	}
	
	public boolean isLabelled()
	{
		return labelled;
	}
	
	public boolean isMultiLabel()
	{
		return multilabel;
	}
	
	public Path getTextFilePath()
	{
		return textFilePath;
	}

	public Path getExtraFilePath()
	{
		return extraFilePath;
	}
	
	

}
