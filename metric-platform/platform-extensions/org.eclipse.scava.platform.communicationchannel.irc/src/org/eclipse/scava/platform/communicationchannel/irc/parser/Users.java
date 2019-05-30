package org.eclipse.scava.platform.communicationchannel.irc.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Users {

	private static Map<String, Integer> userId;
	private static Map<Integer, Set<String>> idUser;
	
	private static void initialise() {
		if (userId == null) {
			userId = new HashMap<String, Integer>();
			idUser = new HashMap<Integer, Set<String>>();		
		}
	}
	
	public static int getId(String username) {
		initialise();
		if (userId.containsKey(username))
			return userId.get(username);
		int id = idUser.size() + 1;
		userId.put(username, id);
		Set<String> set = new HashSet<String>();
		set.add(username);
		idUser.put(id, set);
		return id;
	}
	
	public static int alsoKnownAs(String username, String alias) {
		initialise();
		int id = -1;
		if (userId.containsKey(username)) {
			id = userId.get(username);
			if (!userId.containsKey(alias)) {
				userId.put(alias, id);
				idUser.get(id).add(alias);
			}
		} else {
			if (userId.containsKey(alias)) {
				id = userId.get(alias);
				userId.put(username, id);
				idUser.get(id).add(username);
			} else {
				id = idUser.size() + 1;
				userId.put(username, id);
				userId.put(alias, id);
				Set<String> set = new HashSet<String>();
				set.add(username);
				set.add(alias);
				idUser.put(id, set);
			}
		}
		return id;
	}
	
	public static int lookUpId(String candidate) {
		initialise();
		if (userId.containsKey(candidate))
			return userId.get(candidate);
		return -1;
	}
	
}
