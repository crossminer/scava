package org.eclipse.scava.platform.questionanswering.stackexchange.filters;

import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.HashMap;

public class FiltersUtils
{
	public static HashMap<String, Calendar> dateRangerCreator(String inferior, String superior) throws DateTimeParseException 
	{
		Calendar inferiorDate = Calendar.getInstance();
		Calendar superiorDate = Calendar.getInstance();
		if(inferior==null || inferior.isEmpty())
			inferiorDate=null;
		else
			parseStringDate(inferior,inferiorDate);
		if(superior==null || superior.isEmpty())
			superiorDate=null;
		else
			parseStringDate(superior,superiorDate);
		if(superiorDate == null && inferiorDate==null)
			return null;
		HashMap<String, Calendar> interval = new HashMap<String, Calendar>();
		interval.put("inferior", inferiorDate);
		interval.put("superior", superiorDate);
		return interval;
	}
	
	private static void parseStringDate(String date,Calendar calendar)
	{
		String[] splitDate = date.split("/|-");
		calendar.set(Integer.valueOf(splitDate[2]), Integer.valueOf(splitDate[1]),Integer.valueOf(splitDate[0]));
	}
}
