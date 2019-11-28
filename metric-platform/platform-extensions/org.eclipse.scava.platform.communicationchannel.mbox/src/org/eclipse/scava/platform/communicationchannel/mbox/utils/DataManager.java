package org.eclipse.scava.platform.communicationchannel.mbox.utils;

import java.util.Hashtable;

public class DataManager {

	private static Hashtable<String, Data> downloads;
	
	static {
		downloads = new Hashtable<String, Data>();
	}
	
	public static synchronized Data getData(String id)
	{
		if(!downloads.containsKey(id))
			downloads.put(id, new Data());
		return downloads.get(id);
	}
	
	public static synchronized void removeData(String id)
	{
		if(downloads.containsKey(id))
			downloads.remove(id);
	}
	
	public static synchronized void updateData(String id, Data data)
	{
		downloads.put(id, data);
	}
	
	
}
