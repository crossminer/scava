package org.eclipse.scava.platform.communicationchannel.irc.parser;

import java.util.HashMap;
import java.util.Map;

public class Action {

	private String user, time, text;
	private int userId, actionId;
	private Map<String, Integer> mentionedUsers;
	
	public Action(int id, String time, String line) {
		super();
		actionId = id;
		this.time = time;
		mentionedUsers = new HashMap<String, Integer>();
		parse(line);
	}

	private void parse(String line) {
		int spaceIndex = line.indexOf(" ", 4);
		user = line.substring(4, spaceIndex);
		userId = Users.getId(user);
		text = line.substring(4);
		checkForMentionedUsers();
	}

	private void checkForMentionedUsers() {
		int userIndex = text.indexOf(" ");
		String searchString = text.substring(userIndex);
		for (String word: searchString.split(" ")) {
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

	public String getUser() {
		return user;
	}

	public String getTime() {
		return time;
	}

	public String getText() {
		return text;
	}

	public int getUserId() {
		return userId;
	}

	public int getId() {
		return actionId;
	}	

/*
	public void print() {
		System.out.println("|time|username|userId|text|: " + time + "|" + user + "|" + userId + "|" + text + "|");
		for (String user: mentionedUsers.keySet()) {
			System.out.println("\tmentionedUser|id|: |" + user + "|" + mentionedUsers.get(user) + "|");
		}
	}
*/
}
