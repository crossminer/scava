package org.eclipse.scava.crossflow.tests.crawler;

import java.util.ArrayList;
import java.util.List;

public class UrlCollector extends UrlCollectorBase {
	
	protected List<String> locations = new ArrayList<>();
	
	@Override
	public Url consumeUrls(Url url) {
		
		if (!locations.contains(url.getLocation())) {
			locations.add(url.location);
			return new Url(url.location);
		}
		return null;
		
	}

	public List<String> getLocations() {
		return locations;
	}
	
}