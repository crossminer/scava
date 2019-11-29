package org.eclipse.scava.platform.communicationchannel.sympa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.communicationchannel.sympa.downloader.MyAuthenticator;
import org.eclipse.scava.platform.communicationchannel.sympa.downloader.TgzExtractor;
import org.eclipse.scava.platform.communicationchannel.sympa.parser.Email;
import org.eclipse.scava.platform.communicationchannel.sympa.parser.SympaPlainTextFileReader;
import org.eclipse.scava.platform.communicationchannel.sympa.utils.SympaUtils;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelData;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataManager;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataPath;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;

import com.mongodb.DB;

public class SympaManager implements ICommunicationChannelManager<SympaMailingList> {

	private static OssmeterLogger logger;
	
	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicationchannel.mbox");
	}

	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {

		return communicationChannel instanceof SympaMailingList;
	}

	@Override
	public Date getFirstDate(DB db, SympaMailingList communicationChannel) throws Exception {

		return null;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, SympaMailingList sympa, Date date) throws Exception {

		CommunicationChannelDelta delta = new CommunicationChannelDelta();

		delta.setCommunicationChannel(sympa);

 		for (SympaEmail sympaEmail : getAnalysisDateMail(sympa, date)) {
 	
			delta.getArticles().add(sympaEmail);
		}

		return delta;
	}

	private List<Email> getMailingList(SympaMailingList sympa, Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date.toJavaDate());
		
		int year = calendar.get(Calendar.YEAR);
		String month = SympaUtils.checkDateValue(calendar.get(Calendar.MONTH));
		String day = SympaUtils.checkDateValue(calendar.get(Calendar.DAY_OF_MONTH));
		String ext = SympaUtils.checkExtension(sympa.getCompressedFileExtension());
		
		if(ext.isEmpty())
		{
			logger.error("File extension is empty. Returning empty delta.");
			return new ArrayList<Email>();
		}
		
		CommunicationChannelData data = CommunicationChannelDataManager.getData(sympa.getOSSMeterId());
		
		CommunicationChannelDataPath dataPath;
		
		if(!data.compareDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), year) || !data.fileExists())
		{
			String downloadLink = sympa.getUrl();
			if(!downloadLink.endsWith("/"))
				downloadLink+="/";
			
			downloadLink+=sympa.getMailingListName() + "-" + year + "-" + month + "-" + day	+ ext;

			if (sympa.getUsername().isEmpty() || sympa.getPassword().isEmpty())
				dataPath=downloadMailingList(data.getTempDir(), downloadLink, ext);
			else
				dataPath=downloadMailingList(data.getTempDir(), downloadLink, sympa.getUsername(), sympa.getPassword(), ext);
			if(dataPath!=null)
			{
				data.updateDataPath(dataPath, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), year);
				CommunicationChannelDataManager.updateData(sympa.getOSSMeterId(), data);
			}
		}
		else
			dataPath=data.getDataPath();
		
		if(dataPath==null)
		{
			logger.error("File not found. Returning empty delta.");
			return new ArrayList<Email>();
		}
	 
		try {
			return SympaPlainTextFileReader.parseFolder(dataPath.getPath());
		} catch (IOException e) {
			logger.error("Error whule parsing stored file. Returning empty delta.", e);
			return new ArrayList<Email>();
		}
	}

	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 * @return 
	 */
	private CommunicationChannelDataPath downloadMailingList(Path tempDir, String downloadURL, String ext) {

		CommunicationChannelDataPath dataPath=null;
		try {

			URL url = new URL(downloadURL);
			InputStream is = url.openStream();
			dataPath = TgzExtractor.extract(tempDir, is, ext);
			is.close();

		} catch (MalformedURLException e) {
			logger.error("Malformed URL:"+ downloadURL, e);
		} catch (FileNotFoundException e) {
			logger.error("Error while reading a file", e);
		} catch (IOException e) {
			logger.error(e);
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
	private CommunicationChannelDataPath downloadMailingList(Path tempDir, String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		
		return downloadMailingList(tempDir, downloadURL, ext);

	}

	/**
	 * Returns only the list of emails that match the date of analysis
	 * 
	 * @param sympa
	 * @param date
	 * @return analysisDateMail
	 */
	private List<SympaEmail> getAnalysisDateMail(SympaMailingList sympa, Date date) {

		List<SympaEmail> analysisDateMail = new ArrayList<>();
		for (Email mail : getMailingList(sympa, date)) {

			if (date.compareTo(new Date(mail.getDate())) == 0) {

				SympaEmail sympaEmail = new SympaEmail(mail, sympa);
				analysisDateMail.add(sympaEmail);
			}

		}

		return analysisDateMail;
	}

	/**
	 * 
	 * Determines the date of the earliest email
	 * 
	 * @param mailList
	 * @return earliestDate
	 */
	public static Date determineFirstDate(List<Email> mailList) throws NullPointerException {

		java.util.Date earliestDate = null;
		for (Email mail : mailList) {
			if (!(earliestDate == null)) {
				if (mail.getDate().compareTo(earliestDate) < 0) {
					earliestDate = mail.getDate();
				}
			} else {
				earliestDate = mail.getDate();
			}
		}
		Date firstDate = null;
		try {
			firstDate = new Date(earliestDate.toString());
		} catch (ParseException e) {
			logger.error("Error while parsing a data: ", e);
		}
		return firstDate;
	}

	@Override // NOT USED
	public String getContents(DB db, SympaMailingList communicationChannel, CommunicationChannelArticle article)
			throws Exception {

		return null;
	}


}
