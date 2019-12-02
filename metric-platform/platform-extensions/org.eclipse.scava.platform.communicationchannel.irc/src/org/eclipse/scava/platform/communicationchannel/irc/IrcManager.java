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
import java.util.Calendar;
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
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelData;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataManager;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataPath;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.irc.Irc;

import com.mongodb.DB;

public class IrcManager implements ICommunicationChannelManager<Irc> {
	
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
	

	private CommunicationChannelDataPath getMessageData(Irc irc, Date date) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.toJavaDate());
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String extension = irc.getCompressedFileExtension();
		
		if(extension.isEmpty())
		{
			logger.error("File extension is empty. Returning empty delta.");
			return null;
		}
		
		CommunicationChannelData data = CommunicationChannelDataManager.getData(irc.getOSSMeterId());
		
		CommunicationChannelDataPath dataPath;
		
		if(!data.compareDate(day, month, year) || !data.fileExists())
		{
		
			String downloadLink = irc.getUrl();
			if (!irc.getUrl().endsWith("/"))
				downloadLink += "/";
				
			downloadLink += irc.getName() + "-" + date + extension;
	
			// Determines which download method to use i.e with or without authentication.
			if (irc.getUsername()!="" || irc.getPassword()!="") {
				dataPath=downloadMessage(data.getTempDir(), downloadLink,  irc.getCompressedFileExtension());
	
			} else {
				dataPath=downloadMessage(data.getTempDir(), downloadLink, irc.getUsername(), irc.getPassword(), irc.getCompressedFileExtension());
	
			}
			if(dataPath!=null)
			{
				data.updateDataPath(dataPath, day, month, year);
				CommunicationChannelDataManager.updateData(irc.getOSSMeterId(), data);
			}
		}
		else
			dataPath=data.getDataPath();
		
		return dataPath;
		
	}

	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 */
	private  CommunicationChannelDataPath downloadMessage(Path tempDir,String downloadURL, String ext) {

		CommunicationChannelDataPath dataPath=null;
		try {

			URL url = new URL(downloadURL);
			InputStream is = url.openStream();
			dataPath = TgzExtractor.extract(tempDir, is, ext);
			is.close();

		} catch (MalformedURLException e) {

			logger.error("Malformed URL: ", e);

		} catch (FileNotFoundException e) {
			
			logger.error("File not found: ", e);
		} catch (IOException e) {

			logger.error("IO exception: ", e);
		}
		return dataPath;

	}

	/**
	 * Download Data from URL using authentication
	 * 
	 * @param downloadURL
	 * @param username
	 * @param password
	 */
	private CommunicationChannelDataPath downloadMessage(Path tempDir, String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		
		return downloadMessage(tempDir, downloadURL, ext);
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
		CommunicationChannelDataPath dataPath = getMessageData(irc, date);
		if(dataPath==null)
			return analysisDateMessage;
		
		try {

			Stream<Path> filePaths = Files.walk(dataPath.getPath());

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
			
			filePaths.close();
			

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
