package org.eclipse.scava.platform.communicationchannel.sympa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
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

	Path rootPath;
	Boolean deltaFlag = false;

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

		LocalDate localDate = date.toJavaDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int year = localDate.getYear();
		String month = SympaUtils.checkDateValue(localDate.getMonthValue());
		String day = SympaUtils.checkDateValue(localDate.getDayOfMonth());
		String ext = SympaUtils.checkExtension(sympa.getCompressedFileExtension());

		String downloadLink = "";

		if (!sympa.getUrl().endsWith("/")) {
			downloadLink = sympa.getUrl() + "/" + sympa.getMailingListName() + "-" + year + "-" + month + "-" + day
					+ ext;

		} else {

			downloadLink = sympa.getUrl() + sympa.getMailingListName() + "-" + year + "-" + month + "-" + day + ext;
		}

		if (sympa.getUsername().equals("null") || sympa.getPassword().equals("null")) {

			downloadMailingList(downloadLink, ext);

		} else {

			downloadMailingList(downloadLink, sympa.getUsername(), sympa.getPassword(), ext);

		}

		if (deltaFlag == false) {

			 
			return SympaPlainTextFileReader.parseFolder(rootPath);
		}

		return new ArrayList<Email>();
	}

	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 */
	private void downloadMailingList(String downloadURL, String ext) {

		deltaFlag = false;
		URL url = null;

		try {

			url = new URL(downloadURL);
			InputStream is = url.openStream();
			rootPath = TgzExtractor.extract(is, ext);
			is.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (FileNotFoundException e) {

			// Wait for specified tmie and try again
			try {

				url = new URL(downloadURL);
				InputStream is = url.openStream();
				rootPath = TgzExtractor.extract(is, ext);
				is.close();

			} catch (MalformedURLException e1) {

				e.printStackTrace();

			} catch (FileNotFoundException e1) {

				deltaFlag = true;

			} catch (IOException e1) {

				e.printStackTrace();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Download Data from URL using authentication
	 * 
	 * @param downloadURL
	 * @param username
	 * @param password
	 */
	private void downloadMailingList(String downloadURL, String username, String password, String ext) {

		MyAuthenticator.setPasswordAuthentication(username, password);
		Authenticator.setDefault(new MyAuthenticator());
		deltaFlag = false;
		URL url = null;

		try {

			url = new URL(downloadURL);
			InputStream is = url.openStream();
			rootPath = TgzExtractor.extract(is, ext);
			is.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (FileNotFoundException e) {

			// Wait for specified tmie and try again
			try {

				url = new URL(downloadURL);
				InputStream is = url.openStream();
				rootPath = TgzExtractor.extract(is, ext);
				is.close();

			} catch (MalformedURLException e1) {

				e.printStackTrace();

			} catch (FileNotFoundException e1) {
				deltaFlag = true;
				System.err.println("no archive found at :" + downloadURL);
			} catch (IOException e1) {

				e.printStackTrace();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

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
			e.printStackTrace();
		}
		return firstDate;
	}

	@Override // NOT USED
	public String getContents(DB db, SympaMailingList communicationChannel, CommunicationChannelArticle article)
			throws Exception {

		return null;
	}

}
