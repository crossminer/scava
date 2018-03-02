package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class Token extends Pongo {
	
	protected User user = null;
	
	// protected region custom-fields-and-methods on begin
	// public Date getCreated() {
	// 	Object d = dbObject.get("created");
	// 	if (d == null) return null;
	// 	else return (Date)d;
	// }
	// public Date getExpires() {
	// 	Object d = dbObject.get("expires");
	// 	if (d == null) return null;
	// 	else return (Date)d;
	// }
	// public Token setCreated(Date created) {
	// 	dbObject.put("created", created);
	// 	notifyChanged();
	// 	return this;
	// }
	// public Token setExpires(Date expires) {
	// 	dbObject.put("expires", expires);
	// 	notifyChanged();
	// 	return this;
	// }
	// protected region custom-fields-and-methods end
	
	public Token() { 
		super();
		dbObject.put("user", new BasicDBObject());
		TOKEN.setOwningType("model.Token");
		TYPE.setOwningType("model.Token");
		CREATED.setOwningType("model.Token");
		EXPIRES.setOwningType("model.Token");
	}
	
	public static StringQueryProducer TOKEN = new StringQueryProducer("token"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer CREATED = new StringQueryProducer("created"); 
	public static StringQueryProducer EXPIRES = new StringQueryProducer("expires"); 
	
	
	public String getToken() {
		return parseString(dbObject.get("token")+"", "");
	}
	
	public Token setToken(String token) {
		dbObject.put("token", token);
		notifyChanged();
		return this;
	}
	public TokenType getType() {
		TokenType type = null;
		try {
			type = TokenType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public Token setType(TokenType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public Date getCreated() {
		return parseDate(dbObject.get("created")+"", null);
	}
	
	public Token setCreated(Date created) {
		dbObject.put("created", created);
		notifyChanged();
		return this;
	}
	public Date getExpires() {
		return parseDate(dbObject.get("expires")+"", null);
	}
	
	public Token setExpires(Date expires) {
		dbObject.put("expires", expires);
		notifyChanged();
		return this;
	}
	
	
	
	public Token setUser(User user) {
		if (this.user != user) {
			if (user == null) {
				dbObject.put("user", new BasicDBObject());
			}
			else {
				createReference("user", user);
			}
			this.user = user;
			notifyChanged();
		}
		return this;
	}
	
	public User getUser() {
		if (user == null) {
			user = (User) resolveReference("user");
		}
		return user;
	}
	
}