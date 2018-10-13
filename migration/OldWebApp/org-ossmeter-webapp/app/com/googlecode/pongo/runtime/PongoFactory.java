package com.googlecode.pongo.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ReferenceMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

public class PongoFactory {
	
	protected static PongoFactory instance = new PongoFactory();
	protected ReferenceMap cache = null;
	protected List<PongoFactoryContributor> contributors = new ArrayList<PongoFactoryContributor>();
	
	private PongoFactory(){
		cache = new ReferenceMap(ReferenceMap.HARD, ReferenceMap.SOFT);
		getContributors().add(new ClasspathPongoFactoryContributor());
	}
	
	public static PongoFactory getInstance() {
		return instance;
	}
	
	public Pongo createPongo(DBObject dbObject) {
		return createPongo(dbObject, null);
	}
	
	public void clear() {
		cache.clear();
	}
	
	public Pongo resolveReference(Object ref) {
		if (ref instanceof DBRef) {
			DBRef dbRef = (DBRef) ref;
		
			String fullyQualifiedId = dbRef.getDB().getName() + "." + dbRef.getRef() + "." + dbRef.getId().toString();
			Pongo pongo = (Pongo) cache.get(fullyQualifiedId);
			if (pongo == null) {
				DBObject dbObject = dbRef.getDB().getCollection(dbRef.getRef()).findOne(new BasicDBObject("_id", dbRef.getId()));
				if (dbObject != null) {
					pongo = createPongo(dbObject, dbRef.getDB().getCollection(dbRef.getRef()));
				}
			}
			return pongo;
		}
		else {
			return null;
		}
	}
	
	public Pongo createPongo(DBObject dbObject, DBCollection dbCollection) {
		
		if (dbObject == null) return null;
		
		try {
			String fullyQualifieId = getFullyQualifiedId(dbObject, dbCollection);
			Pongo pongo = (Pongo) cache.get(fullyQualifieId);
			if (fullyQualifieId == null || pongo == null) {
				String className = dbObject.get("_type") + "";
				pongo = createPongo(className);
				pongo.dbObject = dbObject;
				if (fullyQualifieId != null) {
					cache.put(fullyQualifieId, pongo);
				}
			}
			return pongo;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	protected String getFullyQualifiedId(DBObject dbObject, DBCollection dbCollection) {
		if (dbCollection == null) return null;
		return dbCollection.getDB().getName() + "." + dbCollection.getName() + "." + dbObject.get("_id");
	}
	
	protected Pongo createPongo(String className) throws Exception {
		for (PongoFactoryContributor contributor : contributors) {
			if (contributor.canCreate(className)) {
				return contributor.create(className);
			}
		}
		
		throw new RuntimeException("Could not create pongo for class " + className);
		
	}
	
	public List<PongoFactoryContributor> getContributors() {
		return contributors;
	}
}
