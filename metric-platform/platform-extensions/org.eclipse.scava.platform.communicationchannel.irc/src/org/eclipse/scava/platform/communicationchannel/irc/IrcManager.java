package org.eclipse.scava.platform.communicationchannel.irc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.irc.downloader.MyAuthenticator;
import org.eclipse.scava.platform.communicationchannel.irc.downloader.TgzExtractor;
import org.eclipse.scava.platform.communicationchannel.irc.parser.DayChannel;
import org.eclipse.scava.platform.communicationchannel.irc.parser.Message;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.irc.Irc;

import com.mongodb.DB;

public class IrcManager implements ICommunicationChannelManager<Irc> {
	
	private Path rootPath;
	
	protected OssmeterLogger logger;
	
	public IrcManager() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.irc");
	}

	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {

		return communicationChannel instanceof Irc;
	}

	@Override
	public Date getFirstDate(DB db, Irc irc) throws Exception {
		return null;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, Irc irc, Date date)
			throws Exception {
		CommunicationChannelDelta delta = new CommunicationChannelDelta();

		delta.setCommunicationChannel(irc);

		for (IrcMessage ircMessage : getAnalysisDateMessage(irc, date)) {

			delta.getArticles().add(ircMessage);
		}
		
		return delta;
	}
	

	private boolean getMessageData(Irc irc, Date date) {
		
		String downloadLink = irc.getUrl();

		if (!irc.getUrl().endsWith("/"))
			downloadLink += "/";
			
		downloadLink += irc.getName() + "-" + date + irc.getCompressedFileExtension();

		// Determines which download method to use i.e with or without authentication.
		if (irc.getUsername()!="" || irc.getPassword()!="") {

			//.tar.gz
			return downloadMessage(downloadLink,  irc.getCompressedFileExtension());

		} else {

			//.tar.gz
			return downloadMessage(downloadLink, irc.getUsername(), irc.getPassword(), irc.getCompressedFileExtension());

		}
		
	}

	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 */
	private boolean downloadMessage(String downloadURL, String ext) {

		URL url = null;

		try {

			url = new URL(downloadURL);
			InputStream is = url.openStream();
			rootPath = TgzExtractor.extract(is, ext);
			is.close();

		} catch (MalformedURLException e) {

			logger.error("Malformed URL: ", e);
			return false;

		} catch (FileNotFoundException e) {
			
			logger.error("File not found: ", e);
			return false;
		} catch (IOException e) {

			logger.error("IO exception: ", e);
			return false;
		}
		return true;

	}

	/**
	 * Download Data from URL using authentication
	 * 
	 * @param downloadURL
	 * @param username
	 * @param password
	 */
	private boolean downloadMessage(String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		URL url = null;
				
		try {

			url = new URL(downloadURL);
			InputStream is = url.openStream();
			rootPath = TgzExtractor.extract(is, ext);
			is.close();

		} catch (MalformedURLException e) {

			logger.error("Malformed URL: ", e);
			return false;

		} catch (FileNotFoundException e) {
			
			logger.error("File not found: ", e);
			return false;
		} catch (IOException e) {

			logger.error("IO exception: ", e);
			return false;
		}
		return true;
	}

	/**
	 * Returns only the list of Messages that match the date of analysis
	 * 
	 * @param irc
	 * @param date
	 * @return analysisDateMessage
	 */
	private List<IrcMessage> getAnalysisDateMessage(Irc irc, Date date) {

		List<IrcMessage> analysisDateMessage = new ArrayList<>();
		
		if(!getMessageData(irc, date))
			return analysisDateMessage;
		
		Stream<Path> filePaths;
		
		try {

			filePaths = Files.walk(rootPath);

			for (Path path : filePaths.filter(Files::isRegularFile).toArray(Path[]::new))

			{
				File file = path.toFile();

				if (file.isDirectory())
					continue;

				DayChannel dayChannel = new DayChannel(file, date, logger);
				
				Map<String, Message> articles = dayChannel.getMessages();
				
				for(Message message : articles.values())
				{
					IrcMessage ircMessage = new IrcMessage(message, irc);
					analysisDateMessage.add(ircMessage);
					
				}
				

				file.delete();
			}
			
//			This reader clears up after it's self...
//			File root = Paths.get(inputFolder).toFile();
//			root.delete();
			

		} catch (IOException e) {

			logger.error("IO exception:", e);
		}
		
		return analysisDateMessage;
	}


	@Override //Un-used
	public String getContents(DB db, Irc communicationChannel, CommunicationChannelArticle article) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}


}
