package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectCollection extends PongoCollection<Project> {
	
	public ProjectCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("id");
		createIndex("name");
	}
	
	public Iterable<Project> findById(String id) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Project> findByIdentifier(String q) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("id", q + ""))));
	}
	
	public Project findOneByIdentifier(String q) {
		Project project = (Project) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("id", q + "")));
		if (project != null) {
			project.setPongoCollection(this);
		}
		return project;
	}
	

	public long countById(String q) {
		return dbCollection.count(new BasicDBObject("id", q + ""));
	}
	public Iterable<Project> findByName(String q) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Project findOneByName(String q) {
		Project project = (Project) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (project != null) {
			project.setPongoCollection(this);
		}
		return project;
	}
	
	public Iterable<Project> findAnalysed() {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("analysed", true))));
	}

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Project> iterator() {
		return new PongoCursorIterator<Project>(this, dbCollection.find());
	}
	
	public void add(Project project) {
		super.add(project);
	}
	
	public void remove(Project project) {
		super.remove(project);
	}
	
}