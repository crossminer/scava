package org.eclipse.scava.nlp.tools.webcrawler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.util.ByteArrayBuffer;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.exceptions.ParseException;
import edu.uci.ics.crawler4j.parser.NotAllowedContentException;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.util.Util;

public class CrawlerParser extends Parser{

	private WebClient webClient;
	private int maxBytes;
	
	public CrawlerParser(CrawlConfig config) throws IllegalAccessException, InstantiationException {
		super(config);
		maxBytes=config.getMaxDownloadSize();
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setPopupBlockerEnabled(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
        //webClient.setJavaScriptTimeout(1000);
	}
	
	@Override
	public void parse(Page page, String contextURL) throws NotAllowedContentException, ParseException
	{
		HtmlPage htmlPage=null;
		if (!(Util.hasBinaryContent(page.getContentType()) || Util.hasPlainTextContent(page.getContentType()) )) //HTML
		{
			//We need to catch the javascript
			try {
				htmlPage =webClient.getPage(page.getWebURL().getURL());
				executeJS(page, htmlPage);
			} catch (FailingHttpStatusCodeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.parse(page, contextURL);
		if(htmlPage!=null)
		{
			String anchor;
			Set<WebURL> modifiedOutgoingUrls = new HashSet<WebURL>();
			for(WebURL outgoingUrl : page.getParseData().getOutgoingUrls())
			{
				anchor = outgoingUrl.getAttribute("href");
				if(anchor.isEmpty())
				{
					//modifiedOutgoingUrls.add(outgoingUrl);
					continue;
				}	
				try {
					outgoingUrl.setURL(htmlPage.getAnchorByHref(anchor).click().getUrl().toString());
				} catch (ElementNotFoundException | IOException | NullPointerException | FailingHttpStatusCodeException e) {
					//modifiedOutgoingUrls.add(outgoingUrl);
				}
			}
			page.getParseData().setOutgoingUrls(modifiedOutgoingUrls);
				
		}
		
	}
	
	private void executeJS(Page page, HtmlPage htmlPage) throws IOException
	{
		if (htmlPage == null) {
			 page.setContentData(new byte[0]);
        }
		boolean truncated = false;
		String input = htmlPage.getPage().asXml();
		try (InputStream is = new ByteArrayInputStream(input.getBytes())) {
            int size = input.length();
            int readBufferLength = size;

            if (readBufferLength <= 0) {
                readBufferLength = 4096;
            }
            // in case when the maxBytes is less than the actual page size
            readBufferLength = Math.min(readBufferLength, maxBytes);

            // We allocate the buffer with either the actual size of the entity (if available)
            // or with the default 4KiB if the server did not return a value to avoid allocating
            // the full maxBytes (for the cases when the actual size will be smaller than maxBytes).
            ByteArrayBuffer buffer = new ByteArrayBuffer(readBufferLength);

            byte[] tmpBuff = new byte[4096];
            int dataLength;

            while ((dataLength = is.read(tmpBuff)) != -1) {
                if (maxBytes > 0 && (buffer.length() + dataLength) > maxBytes) {
                    truncated = true;
                    dataLength = maxBytes - buffer.length();
                }
                buffer.append(tmpBuff, 0, dataLength);
                if (truncated) {
                    break;
                }
            }
            page.setContentData(buffer.toByteArray());
        }
	}

}
