package org.eclipse.scava.platform.storage.communicationchannel;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CommunicationChannelData {

	private int year;
	private int month;
	private int day;
	private CommunicationChannelDataPath dataPath;
	private Path tempDir;
	
	public CommunicationChannelData(Path tempDir)
	{
		dataPath=null;
		year=-1;
		month=-1;
		day=-1;
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
	
	public boolean compareDate(int day, int month, int year)
	{
		return (this.month==month && this.year==year && this.day==day);
	}
	
	public Long differenceDays(int day, int month, int year)
	{
		if(this.year==-1)
			return null;
		//Months must start from 1
		LocalDate currentStored = LocalDate.of(this.year, this.month, this.day);
		LocalDate searched = LocalDate.of(year, month, day);
		return ChronoUnit.DAYS.between(searched, currentStored);
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
	
	public void updateDataPath(CommunicationChannelDataPath dataPath, int day, int month, int year)
	{
		if(this.dataPath!=null)
			deleteFile();
		this.dataPath=dataPath;
		this.month=month;
		this.year=year;
		this.day=day;
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
