package org.eclipse.scava.nlp.tools.webcrawler;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.authentication.AuthInfo;
import edu.uci.ics.crawler4j.crawler.authentication.FormAuthInfo;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {
	
	private CrawlController.WebCrawlerFactory<CrawlerCore> factory;
	private CrawlController controller;
	private CrawlerCore core;
	
	public Crawler(File storing, List<String> urlSeeds, int maxDept, int maxPages)
	{
		CrawlConfig config = new CrawlConfig();
		config.setIncludeHttpsPages(true);
		config.setPolitenessDelay(1000);
		config.setCrawlStorageFolder(storing.toString());
		config.setMaxDepthOfCrawling(maxDept);
        config.setMaxPagesToFetch(maxPages);       
        
        createCrawler(config, storing, urlSeeds);
	}
	
	public Crawler(File storing, List<String> urlSeeds, int maxDept, int maxPages,String loginURL, String username, String password, String usernameFieldName, String passwordFieldName) throws MalformedURLException
	{
		CrawlConfig config = new CrawlConfig();
		config.setIncludeHttpsPages(true);
		config.setPolitenessDelay(1000);
		config.setCrawlStorageFolder(storing.toString());
		config.setMaxDepthOfCrawling(maxDept);
		config.addAuthInfo(createAuthethicator(username, password, loginURL, usernameFieldName, passwordFieldName));
        config.setMaxPagesToFetch(maxPages);       
        
        createCrawler(config, storing, urlSeeds);
	}
	
	public Crawler(File storing, List<String> urlSeeds)
	{
		CrawlConfig config = new CrawlConfig();
		config.setIncludeHttpsPages(true);
		config.setPolitenessDelay(1000);
		config.setCrawlStorageFolder(storing.toString());
		config.setMaxDepthOfCrawling(-1);
        config.setMaxPagesToFetch(-1);       
        
        createCrawler(config, storing, urlSeeds);
	}
	
	public Crawler(File storing, List<String> urlSeeds, String loginURL, String username, String password, String usernameFieldName, String passwordFieldName) throws MalformedURLException
	{
		CrawlConfig config = new CrawlConfig();
		config.setIncludeHttpsPages(true);
		config.setPolitenessDelay(1000);
		config.setCrawlStorageFolder(storing.toString());
		config.setMaxDepthOfCrawling(-1);
        config.setMaxPagesToFetch(-1);       
        config.addAuthInfo(createAuthethicator(username, password, loginURL, usernameFieldName, passwordFieldName));
        createCrawler(config, storing, urlSeeds);
	}
	
	private AuthInfo createAuthethicator(String username, String password, String loginURL, String usernameFieldName, String passwordFieldName) throws MalformedURLException
	{
		return new FormAuthInfo(username, password, loginURL,usernameFieldName,passwordFieldName);
	}
	
	
	
	private void createCrawler(CrawlConfig config, File storing, List<String> urlSeeds)
	{
		
		config.setIncludeBinaryContentInCrawling(true);
        config.setResumableCrawling(false);
        config.setMaxDownloadSize(6250000); //50mb
        
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        
        try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			
			for(String seed : urlSeeds)
				controller.addSeed(seed);
			
			core = new CrawlerCore(storing, urlSeeds);
			
			factory = () -> core;
			
			core.getMappingPaths();
			
			
		}  
        catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		controller.start(factory, 2);
	}
	
	public HashMap<String, String> getMappingPaths() {
		return core.getMappingPaths();
	}
	
}
