package org.eclipse.scava.platform.communicationchannel.mbox.utils;

import java.io.File;

public class Data {
	
	int year;
	int month;
	DataPath dataPath;
	
	public Data() {
		dataPath=null;
		year=-1;
		month=-1;
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
	
	public void updateDataPath(DataPath dataPath, int month, int year)
	{
		if(this.dataPath!=null)
			deleteFile();
		this.dataPath=dataPath;
		this.month=month;
		this.year=year;
	}
	
	public DataPath getDataPath() {
		return dataPath;
	}

}
