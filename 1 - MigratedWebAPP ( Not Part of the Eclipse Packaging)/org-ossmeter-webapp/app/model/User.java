package model;

import com.mongodb.*;

import java.util.*;

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

import auth.MongoAuthenticator;
// protected region custom-imports on begin
import be.objectify.deadbolt.core.models.Subject;

public class User extends Pongo implements Subject {
// protected region custom-imports end	
	protected List<Role> roles = null;
	protected List<Permission> permissions = null;
	protected List<LinkedAccount> linkedAccounts = null;
	protected List<Project> watching = null;
	protected List<Project> owns = null;
	protected List<GridEntry> grid = null;
	protected QualityModel qualityModel = null;
	
	// protected region custom-fields-and-methods on begin
	// public Date getLastLogin() {
	// 	Object d = dbObject.get("lastLogin");
	// 	if (d == null) return null;
	// 	else return (Date)d;
	// }
	// public User setLastLogin(Date lastLogin) {
	// 	dbObject.put("lastLogin", lastLogin);
	// 	notifyChanged();
	// 	return this;
	// }
	// public Date getJoinDate() {
	// 	Object d = dbObject.get("lastLogin");
	// 	if (d == null) return null;
	// 	else return (Date)d;
	// }
	
	// public User setJoinDate(Date joinDate) {
	// 	dbObject.put("joinDate", joinDate);
	// 	notifyChanged();
	// 	return this;
	// }

	@Override
	public String getIdentifier() {
		return getEmail();
	}
	// protected region custom-fields-and-methods end
	
	public User() { 
		super();
		dbObject.put("roles", new BasicDBList());
		dbObject.put("permissions", new BasicDBList());
		dbObject.put("linkedAccounts", new BasicDBList());
		dbObject.put("watching", new BasicDBList());
		dbObject.put("owns", new BasicDBList());
		dbObject.put("grid", new BasicDBList());
		dbObject.put("qualityModel", new QualityModel().getDbObject());
		EMAIL.setOwningType("model.User");
		NAME.setOwningType("model.User");
		COMPANY.setOwningType("model.User");
		COUNTRY.setOwningType("model.User");
		EMAILVALIDATED.setOwningType("model.User");
		LASTLOGIN.setOwningType("model.User");
		JOINDATE.setOwningType("model.User");
	}
	
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer SELECTEDQUALITYMODEL = new StringQueryProducer("selectedQualityModel"); 
	public static StringQueryProducer COMPANY = new StringQueryProducer("company"); 
	public static StringQueryProducer COUNTRY = new StringQueryProducer("country"); 
	public static StringQueryProducer EMAILVALIDATED = new StringQueryProducer("emailValidated"); 
	public static StringQueryProducer LASTLOGIN = new StringQueryProducer("lastLogin"); 
	public static StringQueryProducer JOINDATE = new StringQueryProducer("joinDate"); 
	
	
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public User setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	public String getSelectedQualityModel() {
		return parseString(dbObject.get("selectedQualityModel")+"", "");
	}
	
	public User setSelectedQualityModel(String selectedQualityModel) {
		dbObject.put("selectedQualityModel", selectedQualityModel);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public User setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getCompany() {
		return parseString(dbObject.get("company")+"", "");
	}
	
	public User setCompany(String company) {
		dbObject.put("company", company);
		notifyChanged();
		return this;
	}
	public String getCountry() {
		return parseString(dbObject.get("country")+"", "");
	}
	
	public User setCountry(String country) {
		dbObject.put("country", country);
		notifyChanged();
		return this;
	}
	public boolean getEmailValidated() {
		return parseBoolean(dbObject.get("emailValidated")+"", false);
	}
	
	public User setEmailValidated(boolean emailValidated) {
		dbObject.put("emailValidated", emailValidated);
		notifyChanged();
		return this;
	}
	public Date getLastLogin() {
		return parseDate(dbObject.get("lastLogin")+"", null);
	}
	
	public User setLastLogin(Date lastLogin) {
		dbObject.put("lastLogin", lastLogin);
		notifyChanged();
		return this;
	}
	public Date getJoinDate() {
		return parseDate(dbObject.get("joinDate")+"", null);
	}
	
	public User setJoinDate(Date joinDate) {
		dbObject.put("joinDate", joinDate);
		notifyChanged();
		return this;
	}
	
	
	public List<Role> getRoles() {
		if (roles == null) {
			roles = new PongoList<Role>(this, "roles", true);
		}
		return roles;
	}
	public List<Permission> getPermissions() {
		if (permissions == null) {
			permissions = new PongoList<Permission>(this, "permissions", true);
		}
		return permissions;
	}
	public List<LinkedAccount> getLinkedAccounts() {
		if (linkedAccounts == null) {
			linkedAccounts = new PongoList<LinkedAccount>(this, "linkedAccounts", true);
		}
		return linkedAccounts;
	}
	public List<Project> getWatching() {
		if (watching == null) {
			watching = new PongoList<Project>(this, "watching", true);
		}
		return watching;
	}
	public List<Project> getOwns() {
		if (owns == null) {
			owns = new PongoList<Project>(this, "owns", true);
		}
		return owns;
	}
	public List<GridEntry> getGrid() {
		if (grid == null) {
			grid = new PongoList<GridEntry>(this, "grid", true);
		}
		return grid;
	}
	
	public QualityModel getQualityModel() {
		if (qualityModel == null && dbObject.containsField("qualityModel")) {
			qualityModel = (QualityModel) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("qualityModel"));
			qualityModel.setContainer(this);
		}
		return qualityModel;
	}
	public User setQualityModel(QualityModel qualityModel) {
		if (this.qualityModel != qualityModel) {
			if (qualityModel == null) {
				dbObject.removeField("qualityModel");
			}
			else {
				dbObject.put("qualityModel", qualityModel.getDbObject());
			}
			this.qualityModel = qualityModel;
			notifyChanged();
		}
		return this;
	}
	
	public boolean isAdmin() {
		List<String> roles = new ArrayList<String>();	
		for(model.Role role : getRoles()){
        	roles.add(role.getName());
        }
		
		if(roles.contains(MongoAuthenticator.ADMIN_ROLE)) 
			return true;
		else 
			return false;
	}
}