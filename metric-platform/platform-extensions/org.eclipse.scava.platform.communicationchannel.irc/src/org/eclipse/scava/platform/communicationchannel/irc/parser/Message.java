package org.eclipse.scava.platform.communicationchannel.irc.parser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.Date;

public class Message {

	private java.util.Date date;
	private String user, text;
	private int userId;
	private String msgId;
	private String threadId;
	private Map<String, Integer> mentionedUsers;
	private static Pattern timeParser=Pattern.compile("\\[?(\\d{2}):(\\d{2})\\]?");
	
	public Message(String channelName, String line, Date day, int milliSeconds) {
		mentionedUsers = new HashMap<String, Integer>();
		parse(line, day, milliSeconds);
		msgId=channelName+" "+String.valueOf(date.getTime());
		threadId=channelName;
	}
	
	private void parse(String line, Date day, int milliSeconds) {
		int timePos = line.indexOf(" ");
		processDate(day, line.substring(1, timePos-1), milliSeconds);
		int userPos = line.indexOf(" ", timePos+1);
		user = line.substring(timePos+2, userPos-1);
		userId = Users.getId(user);
		text = line.substring(userPos+1);
		checkForMentionedUsers();
	}
	
	private void processDate(Date day, String time, int milliSeconds)
	{
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(day.toJavaDate());
		Matcher m = timeParser.matcher(time);
		if(m.find())
		{
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(m.group(1)));
			calendar.set(Calendar.MINUTE, Integer.valueOf(m.group(2)));
			calendar.set(Calendar.MILLISECOND, milliSeconds);
		}
		date=calendar.getTime();
	}

	private void checkForMentionedUsers() {
		for (String word: text.split(" ")) {
			int id = Users.lookUpId(word);
			if (id > 0) {
				mentionedUsers.put(word, id);
			} else if (word.endsWith(":") || word.endsWith(",") || word.endsWith(".") 
					|| word.endsWith("!") || word.endsWith("?") ) {
				word = word.substring(0, word.length()-1);
				id = Users.lookUpId(word);
				if (id > 0) {
					mentionedUsers.put(word, id);
				}
			}
		}
	}

	public java.util.Date getDate() {
		return date;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public int getUserId() {
		return userId;
	}

	public String getId() {
		return msgId;
	}	
	
	public String getThreadId() {
		return threadId;
	}
/*
	public void print() {
		System.out.println("time|user|id|text|: |" + time + "|" + user + "|" + userId + "|" + text + "|");
		for (String user: mentionedUsers.keySet()) {
			System.out.println("\tmentionedUser|id|: |" + user + "|" + mentionedUsers.get(user) + "|");
		}
	}
*/
}
