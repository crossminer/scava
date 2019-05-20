package org.eclipse.scava.nlp.tools.webcrawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

class CrawlerCore extends WebCrawler {
	
	private Pattern imageExtensions = Pattern.compile("\\.(jpe?g|png|bmp|gif|tiff|ai|raw|psd|indd|psd|eps)$");
	private Pattern zipExtensions = Pattern.compile("\\.(zip|rar|tar|tgz|gz)$");

	
	private List<String> urlSeeds;
	private File storing;
	private HashMap<String,String> mappingPaths;
	
	public CrawlerCore(File storing, List<String> urlSeeds) {
		this.urlSeeds=urlSeeds;
		this.storing=storing;
		this.mappingPaths = new HashMap<String,String>();
	}
	
	@Override
    public boolean shouldVisit(Page referringPage, WebURL url)
	{		
        String urlString = url.getURL();

        if (imageExtensions.matcher(urlString).find())
            return false;
        if (zipExtensions.matcher(urlString).find())
            return false;

        for(String urlSeed : urlSeeds)
        {
        	if(urlString.startsWith(urlSeed))
        			return true;
        }
        return false;
	}
	
	@Override
    public void visit(Page page)
	{
		String extension;
        String pageName = page.getWebURL().getPath();
		if(pageName.isEmpty() || pageName.endsWith("/"))
			extension=".html";
		else
			extension = pageName.substring(pageName.lastIndexOf("."));
		
		String uniqueName = UUID.randomUUID().toString() + extension;

        
        String filename = storing.toString() + '/' + uniqueName;
        
        try
        {
            Files.write(Paths.get(filename),page.getContentData());
            mappingPaths.put(uniqueName, pageName);
            System.err.println("Stored: "+ pageName);
        } 
        catch (IOException e)
        {
        	e.printStackTrace();
        }
	}
	
	public HashMap<String, String> getMappingPaths()
	{
		return mappingPaths;
	} 
	
	
	
	

}
