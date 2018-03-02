package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class LinkedAccount extends Pongo {
	
	
	
	public LinkedAccount() { 
		super();
		PROVIDERUSERID.setOwningType("model.LinkedAccount");
		PROVIDERKEY.setOwningType("model.LinkedAccount");
	}
	
	public static StringQueryProducer PROVIDERUSERID = new StringQueryProducer("providerUserId"); 
	public static StringQueryProducer PROVIDERKEY = new StringQueryProducer("providerKey"); 
	
	
	public String getProviderUserId() {
		return parseString(dbObject.get("providerUserId")+"", "");
	}
	
	public LinkedAccount setProviderUserId(String providerUserId) {
		dbObject.put("providerUserId", providerUserId);
		notifyChanged();
		return this;
	}
	public String getProviderKey() {
		return parseString(dbObject.get("providerKey")+"", "");
	}
	
	public LinkedAccount setProviderKey(String providerKey) {
		dbObject.put("providerKey", providerKey);
		notifyChanged();
		return this;
	}
	
	
	
	
}