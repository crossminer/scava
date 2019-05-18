package org.eclipse.scava.platform.documentation.systematic;

import java.util.Calendar;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDocumentation;
import org.eclipse.scava.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;

import com.mongodb.DB;

public class DocumentationSystematicManager implements ICommunicationChannelManager<DocumentationSystematic> {

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
		
		if(!documentation.getLogin().isEmpty())
		{
			documentation.setLogin(documentationSystematic.getLogin());
			documentation.setPassword(documentationSystematic.getPassword());
		}
		
		delta.getDocumentation().add(documentation);
		
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
        calendar.setTime(date.toJavaDate());
        calendar.add(Calendar.DATE, executionFrequency);
        
        return calendar.getTime();
	}
	

}
