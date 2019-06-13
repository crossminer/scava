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
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import com.mongodb.DB;

public class MboxManager implements ICommunicationChannelManager<Mbox>{
	
//	File file;
	Path rootPath;
	Boolean deltaFlag = false;

	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {

		return communicationChannel instanceof Mbox;
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, Mbox mbox, Date date) throws Exception {
		
		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		
		delta.setCommunicationChannel(mbox);


		LocalDate localDate = date.toJavaDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int year = localDate.getYear();
		String month = MboxUtils.checkDateValue(localDate.getMonthValue());
		String day = MboxUtils.checkDateValue(localDate.getDayOfMonth());
		String ext = MboxUtils.checkExtension(mbox.getCompressedFileExtension());

		String downloadLink = "";

		if (!mbox.getUrl().endsWith("/")) {
			downloadLink = mbox.getUrl() + "/" + mbox.getMboxName() + "-" + year + month + day
					+ ext;

		} else {

			downloadLink = mbox.getUrl() + mbox.getMboxName() + "-" + year + month + day + ext;
		}

		if (mbox.getUsername().equals("null") || mbox.getPassword().equals("null")) {

			downloadMbox(downloadLink, ext);

		} else {

			downloadMbox(downloadLink, mbox.getUsername(), mbox.getPassword(), ext);

		}

		if (deltaFlag == false) {
			
			Stream<Path> filePaths;
			
			filePaths = Files.walk(rootPath);
			
			for (Path path : filePaths.filter(Files::isRegularFile).toArray(Path[]::new))
			{
				File file = path.toFile();

				for (Email email : MBoxReader.parseFile(file)) {

					if(email.getDate()!=null) {

						if (date.compareTo(new Date(email.getDate())) == 0) {
	
							MboxEmail mboxEmail = new MboxEmail(email, mbox);
							delta.getArticles().add(mboxEmail);
						}
					}
				}
			}
			
			filePaths.close();
			
		}

		return delta;
	}
	
	/**
	 * Download Data from URL using no authentication
	 * 
	 * @param downloadURL
	 */
	private void downloadMbox(String downloadURL, String ext) {

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
	private void downloadMbox(String downloadURL, String username, String password, String ext) {

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
	
	

	@Override //Not used
	public String getContents(DB db, Mbox communicationChannel, CommunicationChannelArticle article) throws Exception {
		
		return null;
	}

	@Override //Not used
	public Date getFirstDate(DB db, Mbox communicationChannel) throws Exception {
		
		return null;
	}

}
