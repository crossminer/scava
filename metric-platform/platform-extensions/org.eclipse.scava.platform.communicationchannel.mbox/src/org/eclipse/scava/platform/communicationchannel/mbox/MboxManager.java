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
import java.util.TimeZone;
import java.util.stream.Stream;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.mbox.downloader.MyAuthenticator;
import org.eclipse.scava.platform.communicationchannel.mbox.downloader.TgzExtractor;
import org.eclipse.scava.platform.communicationchannel.mbox.parser.Email;
import org.eclipse.scava.platform.communicationchannel.mbox.parser.MBoxReader;
import org.eclipse.scava.platform.communicationchannel.mbox.utils.MboxUtils;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelData;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataManager;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataPath;
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
		
		CommunicationChannelData data = CommunicationChannelDataManager.getData(mbox.getOSSMeterId());
		
		String extension = MboxUtils.checkExtension(mbox.getCompressedFileExtension());
		
		if(extension.isEmpty())
		{
			logger.error("File extension is empty. Returning empty delta.");
			return delta;
		}
		
		CommunicationChannelDataPath dataPath;
		//Case of dumps from Castalia
		if(extension.equals(".mbox.gz"))
		{	
			//In this section, the months have to start from 1
			boolean download=false;
			int day=calendar.get(Calendar.DAY_OF_MONTH);
			month+=1;	//Because months starts in 0
			Long difference = data.differenceDays(day, month, year); 
			
			if(difference==null)
			{
				//The files are updated each sunday at 2AM.
				calendar.setTime(new java.util.Date());
				calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
				int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
				//Thus we will update each Monday
				calendar.add(weekDay, -(weekDay-2));
				
				data.updateDataPath(null, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));
				
				difference = data.differenceDays(day, month, year); 
				download=true;
			}
			
			if(difference<1)
			{
				
				//The files are updated each sunday at 2AM.
				calendar.setTime(new java.util.Date());
				calendar.setTimeZone(TimeZone.getTimeZone("UTC"));	
				int waiting=7-(calendar.get(Calendar.DAY_OF_WEEK)-2);
				
				try {
					logger.info("The dumps need to be updated. The reader will have to wait for " + waiting
							+ " days before downloading the new dumps.");
					waiting*=24*60*60;
					waiting-=(calendar.get(Calendar.HOUR_OF_DAY)*60*60)+(calendar.get(Calendar.MINUTE)*60);
					logger.info("Exactly, the awaiting will be for "+waiting+" seconds.");
					Thread.sleep((waiting * 1000l));
		
				} catch (InterruptedException e) {
					logger.error("An error happened while waiting for the calls limit to renew", e);
				}
				download=true;
			}
			
			if(download || !data.fileExists())
			{
				//The files are updated each sunday at 2AM.
				calendar.setTime(new java.util.Date());
				calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
				int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
				//Thus we will update each Monday
				calendar.add(weekDay, -(weekDay-2));
				
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH)+1;	//Remember day the months start in 0
				day=calendar.get(Calendar.DAY_OF_MONTH);
				
			
				String url = mbox.getUrl() + extension;
				if(mbox.getUsername().isEmpty() || mbox.getPassword().isEmpty())
					dataPath=downloadMbox(data.getTempDir(), url, extension);
				else
					dataPath=downloadMbox(data.getTempDir(),url, mbox.getUsername(), mbox.getPassword(),extension);
				if(dataPath!=null)
				{
					data.updateDataPath(dataPath, day, month, year);
				}
			}
			else
				dataPath=data.getDataPath();
		}
		else
		{
			if(!data.compareDate(month, year) || !data.fileExists())
			{
				String url = mbox.getUrl()+"/"+year+"-"+months.get(month)+extension;
				if(mbox.getUsername().isEmpty() || mbox.getPassword().isEmpty())
					dataPath=downloadMbox(data.getTempDir(), url, extension);
				else
					dataPath=downloadMbox(data.getTempDir(),url, mbox.getUsername(), mbox.getPassword(),extension);
				if(dataPath!=null)
				{
					data.updateDataPath(dataPath, month, year);
					CommunicationChannelDataManager.updateData(mbox.getOSSMeterId(), data);
				}
			}
			else
				dataPath=data.getDataPath();
		}
		
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
	 * @param tempFile 
	 * 
	 * @param downloadURL
	 * @return 
	 */
	private CommunicationChannelDataPath downloadMbox(Path tempDir, String downloadURL, String ext) {

		URL url = null;
		CommunicationChannelDataPath dataPath;
		try {
			
			url = new URL(downloadURL);
			InputStream is = url.openStream();
			dataPath = TgzExtractor.extract(tempDir, is, ext);
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
	private CommunicationChannelDataPath downloadMbox(Path tempDir, String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		
		return downloadMbox(tempDir, downloadURL, ext);

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
