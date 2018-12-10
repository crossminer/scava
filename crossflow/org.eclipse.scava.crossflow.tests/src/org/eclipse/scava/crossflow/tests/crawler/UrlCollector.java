package org.eclipse.scava.crossflow.tests.crawler;

import java.util.ArrayList;
import java.util.List;

public class UrlCollector extends UrlCollectorBase {
	
	protected List<String> locations = new ArrayList<String>();
	
	@Override
	public void consumeUrls(Url url) {
		
		if (!locations.contains(url.getLocation())) {
			locations.add(url.location);
			sendToUrlsToAnalyse(new Url(url.location));
		}
		
	}

	public List<String> getLocations() {
		return locations;
	}
	
}