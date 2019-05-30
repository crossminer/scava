package org.eclipse.scava.platform.documentation.systematic;

import java.util.Calendar;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDocumentation;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.mongodb.DB;

public class DocumentationSystematicManager implements ICommunicationChannelManager<DocumentationSystematic> {

	protected OssmeterLogger logger;
	
	public DocumentationSystematicManager() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.documentation.systematic");
	}
	
	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {
		return (communicationChannel instanceof DocumentationSystematic);
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, DocumentationSystematic documentationSystematic, Date date)
			throws Exception {
		
		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setCommunicationChannel(documentationSystematic);
		
		CommunicationChannelDocumentation documentation = new CommunicationChannelDocumentation();
		
		documentation.setUrl(documentationSystematic.getUrl());
		documentation.setDateDelta(date.toJavaDate());
		documentation.setNextExecutionDate(getNextDateExecution(date, documentationSystematic.getExecutionFrequency()));
		
		if(!documentationSystematic.getUsername().isEmpty() && !documentationSystematic.getPassword().isEmpty())
		{
			try {
				if(documentationSystematic.getUsernameFieldName().isEmpty())
					throw new UnsupportedOperationException("Please set the name of the username field.");
				if(documentationSystematic.getPasswordFieldName().isEmpty())
					throw new UnsupportedOperationException("Please set the name of the password field.");
				if(documentationSystematic.getLoginURL().isEmpty())
					throw new UnsupportedOperationException("Please set the URL where the login has to be done.");
				documentation.setUsername(documentationSystematic.getUsername());
				documentation.setPassword(documentationSystematic.getPassword());
				documentation.setLoginURL(documentationSystematic.getLoginURL());
				documentation.setPasswordFieldName(documentationSystematic.getPasswordFieldName());
				documentation.setUsernameFieldName(documentationSystematic.getUsernameFieldName());
				documentation.setLoginNeeded(true);
				delta.getDocumentation().add(documentation);
			}
			catch(UnsupportedOperationException e)
			{
				logger.error("Error while setting data regarding authentification:", e);
			}
		}
		else
		{
			delta.getDocumentation().add(documentation);
		}
		
		
		return delta;
	}

	@Override
	public String getContents(DB db, DocumentationSystematic communicationChannel, CommunicationChannelArticle article)
			throws Exception {
		return null;
	}

	@Override
	public Date getFirstDate(DB db, DocumentationSystematic communicationChannel) throws Exception {
		return null;
	}
	
	private java.util.Date getNextDateExecution(Date date, int executionFrequency)
	{
		if(executionFrequency==0)
			return date.toJavaDate();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		//Unless the day of analysis is yesterday, the next execution will be the same of the delta
		//The metric thus will avoid the download of data that happened in the past
		if(date.toJavaDate().compareTo(calendar.getTime())==0)
		{	
	        calendar.setTime(date.toJavaDate());
	        calendar.add(Calendar.DATE, executionFrequency);
	        
	        return calendar.getTime();
		}
		else
			return date.toJavaDate();
	}
	

}
