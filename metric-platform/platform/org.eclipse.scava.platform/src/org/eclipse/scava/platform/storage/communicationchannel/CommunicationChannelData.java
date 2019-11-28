package org.eclipse.scava.platform.storage.communicationchannel;

import java.io.File;
import java.nio.file.Path;

public class CommunicationChannelData {

	int year;
	int month;
	CommunicationChannelDataPath dataPath;
	Path tempDir;
	
	public CommunicationChannelData(Path tempDir)
	{
		dataPath=null;
		year=-1;
		month=-1;
		this.tempDir=tempDir;
	}
	
	public boolean fileExists()
	{
		return dataPath.getFile().exists();
	}
	
	public boolean compareDate(int month, int year)
	{
		return (this.month==month && this.year==year);
	}
	
	private void deleteFile()
	{
		File file = dataPath.getFile();
		if(file.exists())
			file.delete();
	}
	
	public void updateDataPath(CommunicationChannelDataPath dataPath, int month, int year)
	{
		if(this.dataPath!=null)
			deleteFile();
		this.dataPath=dataPath;
		this.month=month;
		this.year=year;
	}
	
	public Path getTempDir()
	{
		return tempDir;
	}
	
	public boolean updateTempDir(Path tempDir)
	{
		if(this.tempDir.equals(tempDir))
			return false;
		this.tempDir=tempDir;
		return true;
	}
	
	public CommunicationChannelDataPath getDataPath() {
		return dataPath;
	}
}
