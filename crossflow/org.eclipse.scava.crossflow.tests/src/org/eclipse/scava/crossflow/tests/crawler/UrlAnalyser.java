package org.eclipse.scava.crossflow.tests.crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlAnalyser extends UrlAnalyserBase {
	
	@Override
	public void consumeUrlsToAnalyse(Url url) {
		
		InputStream inputStream = UrlAnalyser.class.getResourceAsStream("pages/" + url.getLocation());
		String html = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
		
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(html);
		while (m.find()) {
			sendToUrls(new Url(m.group(1)));
		}
	}

}