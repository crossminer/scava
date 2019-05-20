package org.eclipse.scava.nlp.tools.webcrawler;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
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
	
	private void createCrawler(CrawlConfig config, File storing, List<String> urlSeeds)
	{
		
		config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);
        
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
	
	public static void main(String[] args) {
		Crawler crawler = new Crawler(new File("C:/Users/diegol/scava/ToDelete/"), Arrays.asList("https://scava-docs.readthedocs.io/en/latest/"));
		crawler.start();
		crawler.getMappingPaths();
		System.err.println("Finished");
	}
	

}
