package org.eclipse.scava.platform.communicationchannel.sympa;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;

import com.mongodb.DB;

public class SympaManager implements ICommunicationChannelManager<SympaMailingList> {
	

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

		for (SympaEmail sympaEmail : getAnalysisDataMail(sympa, date)) {

			delta.getArticles().add(sympaEmail);
		}

		
		
		return delta;
	}

	private List<Email> getMailingList(SympaMailingList sympa, Date date) {
		
		String path = "";
		
		LocalDate localDate = date.toJavaDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		int year = localDate.getYear();
		String month = SympaUtils.checkDateValue(localDate.getMonthValue());
		String day = SympaUtils.checkDateValue(localDate.getDayOfMonth());
		String ext = SympaUtils.checkExtension(sympa.getCompressedFileExtension());
		

		String downloadLink = "";
		
		if (!sympa.getUrl().endsWith("/")) 
		{
			downloadLink = sympa.getUrl() + "/" + sympa.getMailingListName() + "_" + year+ "_" + month + "_" + day + ext;
		
		}else {
			
			downloadLink = sympa.getUrl() + sympa.getMailingListName() + "_" + year+ "_" + month + "_" + day + ext;
		}

		
		if (sympa.getUsername().equals("null") || sympa.getPassword().equals("null")) {

			path = downloadMailingList(downloadLink, ext);

		} else {

			path = downloadMailingList(downloadLink, sympa.getUsername(), sympa.getPassword(), ext);

		}

		
	
		

		return SympaPlainTextFileReader.parseFolder(path+"/");
	}
	



	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 */
	private String downloadMailingList(String downloadURL, String ext ) {

		String rootPath = "";


		URL url = null;

		try {

			url = new URL(downloadURL);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		try {

			InputStream is = url.openStream();
			rootPath = TgzExtractor.extract(is, ext);
			is.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
		
		return rootPath;

	}

	/**
	 * Download Data from URL using authentication
	 * 
	 * @param downloadURL
	 * @param username
	 * @param password
	 */
	private String downloadMailingList(String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		String rootPath = "";
		URL url = null;

		try {

			url = new URL(downloadURL);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		try {

			InputStream is = url.openStream();
			//here handle unzip
			 rootPath = TgzExtractor.extract(is, ext);
			//Files.copy(is, Paths.get(downloadPath), StandardCopyOption.REPLACE_EXISTING);// DL (on the downlow)
			is.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		
		
		return rootPath;
	}

	/**
	 * Returns only the list of emails that match the date of analysis
	 * 
	 * @param sympa
	 * @param date
	 * @return analysisDateMail
	 */
	private List<SympaEmail> getAnalysisDataMail(SympaMailingList sympa, Date date) {

		List<SympaEmail> analysisDateMail = new ArrayList<>();

		for (Email mail : getMailingList(sympa, date)) {

			if ((date.compareTo(mail.getDate()) == 0) == true) {

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
			e.printStackTrace();
		}
		return firstDate;
	}

	@Override // NOT USED
	public String getContents(DB db, SympaMailingList communicationChannel, CommunicationChannelArticle article)
			throws Exception {

		return null;
	}

	

	// T E S T I N G
	public static void main(String[] args) throws Exception {

		SympaManager manager = new SympaManager();

		SympaMailingList sympa = new SympaMailingList();
		// Modify the directory
		sympa.setUrl("http://localhost:8888/download/");
		sympa.setMailingListDescription("testing Y'all");
		sympa.setMailingListName("lemonldap-ng-dev");
		sympa.setCompressedFileExtension("tar.gz");
//		sympa.setUsername("crossminer");
		//sympa.setPassword("U4QRh7H9SFy4AZxa");

		// this simulates the delta creation process
		Date today = new Date();
		//Date date = new Date(manager.getFirstDate(null, sympa).toString());
		Date date = new Date(java.util.Date.parse("05/19/2008"));
		Date date1 = new Date(java.util.Date.parse("06/01/2008"));
		
		System.out.println(date);
		int count = 0;
		System.out.println("Analysis Date: " + date);

		do {
			if (date == date1) {
				
				System.out.println("hello");
			}
			CommunicationChannelDelta delta = new CommunicationChannelDelta();
			delta = manager.getDelta(null, sympa, date);
			System.out.println("\n\tDate :\t" + date + "\tDelta Size :\t" + delta.getArticles().size());
			count = count + delta.getArticles().size();
			date = new Date(date.addDays(1).toString());
		} while (date.compareTo(today) < 1);

		System.out.println("\nTotal Emails: " + count);

	}

}
