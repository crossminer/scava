package org.eclipse.scava.nlp.tools.license;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LicenseInformation {

	private static Map<String, Map<String,License>> hierarchy;
	
	static
	{
		splitData();
	}
	
	private static void splitData()
	{
		Map<String, List<License>> hierarchyTemp = LicenseAnalyser.getHierarchy();
		List<License> licensesTemp;
		Map<String, License> licenses;
		
		
		for(String groupName : hierarchyTemp.keySet())
		{
			licensesTemp = hierarchyTemp.get(groupName);
			licenses = new HashMap<String, License>(licensesTemp.size());
			for(License license : licensesTemp)
			{
				licenses.put(license.getName(), license);
			}
			hierarchy.put(groupName, licenses);
		}
	}
	
	public static License retrieve(String groupName, String licenseName)
	{
		return hierarchy.get(groupName).get(licenseName);
	}
	
}
