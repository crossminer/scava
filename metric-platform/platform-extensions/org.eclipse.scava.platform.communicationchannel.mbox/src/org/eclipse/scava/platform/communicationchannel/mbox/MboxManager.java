package org.eclipse.scava.platform.communicationchannel.mbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.Stream;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.mbox.downloader.MyAuthenticator;
import org.eclipse.scava.platform.communicationchannel.mbox.downloader.TgzExtractor;
import org.eclipse.scava.platform.communicationchannel.mbox.parser.Email;
import org.eclipse.scava.platform.communicationchannel.mbox.parser.MBoxReader;
import org.eclipse.scava.platform.communicationchannel.mbox.utils.Data;
import org.eclipse.scava.platform.communicationchannel.mbox.utils.DataManager;
import org.eclipse.scava.platform.communicationchannel.mbox.utils.DataPath;
import org.eclipse.scava.platform.communicationchannel.mbox.utils.MboxUtils;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;

import com.mongodb.DB;

public class MboxManager implements ICommunicationChannelManager<Mbox>{
	
	private static HashMap<Integer, String> months;
	private static OssmeterLogger logger;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.mbox");
		months= new HashMap<Integer, String>();
		months.put(0, "January");
		months.put(1, "February");
		months.put(2, "March");
		months.put(3, "April");
		months.put(4, "May");
		months.put(5, "June");
		months.put(6, "July");
		months.put(7, "August");
		months.put(8, "September");
		months.put(9, "October");
		months.put(10, "November");
		months.put(11, "December");
	}
	
	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {

		return communicationChannel instanceof Mbox;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, Mbox mbox, Date date) throws Exception {
		
		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		
		delta.setCommunicationChannel(mbox);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.toJavaDate());
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		
		Data data = DataManager.getData(mbox.getOSSMeterId());
		
		String extension = MboxUtils.checkExtension(mbox.getCompressedFileExtension());
		
		if(extension.isEmpty())
		{
			logger.error("File extension is empty. Returning empty delta.");
			return delta;
		}
		
		DataPath dataPath;
		if(!data.compareDate(month, year) || !data.fileExists())
		{
			String url = mbox.getUrl()+"/"+year+"-"+months.get(month)+extension;
			if(mbox.getUsername().isEmpty() || mbox.getPassword().isEmpty())
				dataPath=downloadMbox(url, extension);
			else
				dataPath=downloadMbox(url, mbox.getUsername(), mbox.getPassword(),extension);
			if(dataPath!=null)
			{
				data.updateDataPath(dataPath, month, year);
				DataManager.updateData(mbox.getOSSMeterId(), data);
			}
		}
		else
			dataPath=data.getDataPath();
		
		if(dataPath==null)
		{
			logger.error("File not found. Returning emtpy delta.");
			return delta;
		}
		
		if(dataPath.isFile())
		{
			readFile(dataPath.getFile(), delta, date, mbox);
		}
		else
		{
			Stream<Path> filePaths = Files.walk(dataPath.getPath());
			for (Path path : filePaths.filter(Files::isRegularFile).toArray(Path[]::new))
				readFile(path.toFile(), delta, date, mbox);
			filePaths.close();
		}
		return delta;
	}
	
	private void readFile(File file, CommunicationChannelDelta delta, Date date, Mbox mbox)
	{
		for (Email email : MBoxReader.parseFile(file)) {

			if(email.getDate()!=null) {

				if (date.compareTo(new Date(email.getDate())) == 0) {

					MboxEmail mboxEmail = new MboxEmail(email, mbox);
					delta.getArticles().add(mboxEmail);
				}
			}
		}
	}
	
	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 * @return 
	 */
	private DataPath downloadMbox(String downloadURL, String ext) {

		URL url = null;
		DataPath dataPath;
		try {
			
			url = new URL(downloadURL);
			InputStream is = url.openStream();
			dataPath = TgzExtractor.extract(is, ext);
			is.close();
			return dataPath;

		} catch (MalformedURLException e) {
			logger.error("Malformed URL:"+ downloadURL, e);

		} catch (FileNotFoundException e) {
			logger.error("Error while reading a file", e);
			
		} catch (IOException e) {
			logger.error(e);
		}
		return null;

	}
	
	

	/**
	 * Download Data from URL using authentication
	 * 
	 * @param downloadURL
	 * @param username
	 * @param password
	 */
	private DataPath downloadMbox(String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		
		return downloadMbox(downloadURL, ext);

	}
	
	

	@Override //Not used
	public String getContents(DB db, Mbox communicationChannel, CommunicationChannelArticle article) throws Exception {
		
		return null;
	}

	@Override //Not used
	public Date getFirstDate(DB db, Mbox communicationChannel) throws Exception {
		
		return null;
	}

}
