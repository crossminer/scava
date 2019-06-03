package org.eclipse.scava.platform.communicationchannel.irc.parser;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private String time, user, text;
	private int userId, msgId; 
	private Map<String, Integer> mentionedUsers;
	
	public Message(int id, String line) {
		super();
		msgId = id;
		mentionedUsers = new HashMap<String, Integer>();
		parse(line);
	}
	
	private void parse(String line) {
		int timePos = line.indexOf(" ");
		time = line.substring(1, timePos-1);
		int userPos = line.indexOf(" ", timePos+1);
		user = line.substring(timePos+2, userPos-1);
		userId = Users.getId(user);
		text = line.substring(userPos+1);
		checkForMentionedUsers();
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

	public String getTime() {
		return time;
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

	public int getId() {
		return msgId;
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
