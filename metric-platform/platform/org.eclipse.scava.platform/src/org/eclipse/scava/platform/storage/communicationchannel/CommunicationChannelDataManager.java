package org.eclipse.scava.platform.storage.communicationchannel;

import java.nio.file.Path;
import java.util.Hashtable;

public class CommunicationChannelDataManager {

	private static Hashtable<String, CommunicationChannelData> downloads;
	
	static {
		downloads = new Hashtable<String, CommunicationChannelData>();
	}
	
	public static synchronized void intializeStorage(String id, Path tempDir)
	{
		if(!downloads.containsKey(id))
			downloads.put(id, new CommunicationChannelData(tempDir));
		else
		{
			CommunicationChannelData data = downloads.get(id);
			if(data.updateTempDir(tempDir))
				downloads.put(id, data);
		}
	}
	
	public static synchronized CommunicationChannelData getData(String id)
	{
		return downloads.get(id);
	}
	
	public static synchronized void removeData(String id)
	{
		if(downloads.containsKey(id))
			downloads.remove(id);
	}
	
	public static synchronized void updateData(String id, CommunicationChannelData data)
	{
		downloads.put(id, data);
	}
}
