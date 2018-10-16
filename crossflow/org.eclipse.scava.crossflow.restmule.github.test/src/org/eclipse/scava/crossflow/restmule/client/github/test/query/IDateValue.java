package org.eclipse.scava.crossflow.restmule.client.github.test.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface IDateValue<O> {
	
	static final DateFormat SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	// ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ
	static final DateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+07:00");
	
	static final DateFormat LONGER_HEADER = new SimpleDateFormat("EEE, dd M yyyy HH:mm:ss 'GMT'z");
	
	O between(Date start, Date end);
	O from(Date start);
	O before(Date end);
	O at(Date date);
	
}
