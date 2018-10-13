package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Statistics extends Pongo {
	
	protected List<Demographic> demographics = null;
	
	
	public Statistics() { 
		super();
		dbObject.put("demographics", new BasicDBList());
		DATE.setOwningType("model.Statistics");
		NUMBEROFPROJECTS.setOwningType("model.Statistics");
		NUMBEROFUSERS.setOwningType("model.Statistics");
		NUMBEROFVCSREPOSITORIES.setOwningType("model.Statistics");
		NUMBEROFBUGTRACKERS.setOwningType("model.Statistics");
		NUMBEROFCOMMUNICATIONCHANNELS.setOwningType("model.Statistics");
		NUMBEROFBUGS.setOwningType("model.Statistics");
		NUMBEROFMESSAGES.setOwningType("model.Statistics");
		NUMBEROFCODELINES.setOwningType("model.Statistics");
	}
	
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static NumericalQueryProducer NUMBEROFPROJECTS = new NumericalQueryProducer("numberOfProjects");
	public static NumericalQueryProducer NUMBEROFUSERS = new NumericalQueryProducer("numberOfUsers");
	public static NumericalQueryProducer NUMBEROFVCSREPOSITORIES = new NumericalQueryProducer("numberOfVcsRepositories");
	public static NumericalQueryProducer NUMBEROFBUGTRACKERS = new NumericalQueryProducer("numberOfBugTrackers");
	public static NumericalQueryProducer NUMBEROFCOMMUNICATIONCHANNELS = new NumericalQueryProducer("numberOfCommunicationChannels");
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer NUMBEROFMESSAGES = new NumericalQueryProducer("numberOfMessages");
	public static NumericalQueryProducer NUMBEROFCODELINES = new NumericalQueryProducer("numberOfCodeLines");
	
	
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public Statistics setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public int getNumberOfProjects() {
		return parseInteger(dbObject.get("numberOfProjects")+"", 0);
	}
	
	public Statistics setNumberOfProjects(int numberOfProjects) {
		dbObject.put("numberOfProjects", numberOfProjects);
		notifyChanged();
		return this;
	}
	public int getNumberOfUsers() {
		return parseInteger(dbObject.get("numberOfUsers")+"", 0);
	}
	
	public Statistics setNumberOfUsers(int numberOfUsers) {
		dbObject.put("numberOfUsers", numberOfUsers);
		notifyChanged();
		return this;
	}
	public int getNumberOfVcsRepositories() {
		return parseInteger(dbObject.get("numberOfVcsRepositories")+"", 0);
	}
	
	public Statistics setNumberOfVcsRepositories(int numberOfVcsRepositories) {
		dbObject.put("numberOfVcsRepositories", numberOfVcsRepositories);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugTrackers() {
		return parseInteger(dbObject.get("numberOfBugTrackers")+"", 0);
	}
	
	public Statistics setNumberOfBugTrackers(int numberOfBugTrackers) {
		dbObject.put("numberOfBugTrackers", numberOfBugTrackers);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommunicationChannels() {
		return parseInteger(dbObject.get("numberOfCommunicationChannels")+"", 0);
	}
	
	public Statistics setNumberOfCommunicationChannels(int numberOfCommunicationChannels) {
		dbObject.put("numberOfCommunicationChannels", numberOfCommunicationChannels);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public Statistics setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfMessages() {
		return parseInteger(dbObject.get("numberOfMessages")+"", 0);
	}
	
	public Statistics setNumberOfMessages(int numberOfMessages) {
		dbObject.put("numberOfMessages", numberOfMessages);
		notifyChanged();
		return this;
	}
	public int getNumberOfCodeLines() {
		return parseInteger(dbObject.get("numberOfCodeLines")+"", 0);
	}
	
	public Statistics setNumberOfCodeLines(int numberOfCodeLines) {
		dbObject.put("numberOfCodeLines", numberOfCodeLines);
		notifyChanged();
		return this;
	}
	
	
	public List<Demographic> getDemographics() {
		if (demographics == null) {
			demographics = new PongoList<Demographic>(this, "demographics", true);
		}
		return demographics;
	}
	
	
}