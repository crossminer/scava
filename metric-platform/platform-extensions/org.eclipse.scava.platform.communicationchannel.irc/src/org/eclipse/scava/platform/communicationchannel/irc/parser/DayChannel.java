package org.eclipse.scava.platform.communicationchannel.irc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.platform.Date;

public class DayChannel {
	
	private Map<String, Message> messages;
//	private Map<Integer, Action> actions;
	private String channelName;
	
	private static final Pattern chExp = Pattern.compile("(_[^_]+\\.txt)$");
	private static final Pattern msgExp =  Pattern.compile("^\\[(\\d{2}:\\d{2})\\] <.*> .*$");
							   //aka = " is now known as ",
							   //joined = "  has joined ",
							   //left = "  has left ";
	
	
	public DayChannel(File file, Date date) {
		super();
		messages = new HashMap<String, Message>();
		//actions = new HashMap<Integer, Action>();
		extractChannelName(file);
		parse(file, date);
		
	}

	private void extractChannelName(File file) {
		String fileName = file.getName();
//		channelName = fileName.substring(0, fileName.length() - 4);
//		channelName = fileName.replaceAll("(_[^_]+\\.txt)$", ""); 
		channelName = chExp.matcher(fileName).replaceAll("");
		System.out.println("channelName: " + channelName + "\n");
	}
	
	private void parse(File file, Date date) {

		int millisecondsCounter=0;
		String previousTime="";
		String fileContent = readFileAsString(file);
		Matcher m;
		for (String line: fileContent.split("\n")) {
			m = msgExp.matcher(line);
			if (m.matches()) {
				if(previousTime.equals(m.group(1)))
					millisecondsCounter++;
				else
				{
					millisecondsCounter=0;
					previousTime=m.group(1);
				}
				Message msg = new Message(channelName, line, date, millisecondsCounter);
				messages.put(msg.getId(), msg);
			}
//			else if (line.startsWith("===")) {
//				if (line.contains(aka)) {
//					int akaPos = line.indexOf(aka);
//					String username = line.substring(4, akaPos);
//					String alias = line.substring(akaPos + aka.length());
//					int userId = Users.alsoKnownAs(username, alias);
////					System.out.println("|username|alias|userId|: " + username + "|" + alias + "|" + userId + "|");
//				} else
//				if (line.contains(joined + channelName)) {
////					=== Lure [i=lure@ubuntu/member/lure]  has joined #kubuntu-devel
//					int joinedIndex = line.indexOf(" ", 4);
//					String username = line.substring(4, joinedIndex);
//					int userId = Users.getId(username);
////					System.out.println("JOINED |username|userId|: |" + username + "|" + userId + "|");
//				} else
//				if (line.contains(left + channelName)) {
////					=== jeroenvrp [n=jeroenvr@k-uptown.xs4all.nl]  has left #kubuntu-devel ["The] 
//					int leftIndex = line.indexOf(" ", 4);
//					String username = line.substring(4, leftIndex);
//					int userId = Users.getId(username);
////					System.out.println("\tLEFT |username|userId|: |" + username + "|" + userId + "|");
//				} else {
////					=== Hobbsee attacks Hawkwind with her Long Pointy Stick of DOOM!!!!!!!!!!!!!!!! (tm)
//					int actionId = actions.size() + 1;
//					Action action = new Action(actionId, time, line);
////					action.print();
//					actions.put(action.getId(), action);
//
//				}
//				
//			}			
		}

	}

	public String getChannelName() {
		return channelName;
	}
	
	public Map<String, Message> getMessages() {
		return messages;
	}

	private static String readFileAsString(File afile) {
	    char[] buffer = new char[(int) afile.length()];
	    BufferedReader br = null;
	    try {
	    		br = new BufferedReader(new InputStreamReader(new FileInputStream(afile), "UTF-8"));
	        br.read(buffer);
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
	    	if (br != null) try { br.close(); } catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}

	
}
