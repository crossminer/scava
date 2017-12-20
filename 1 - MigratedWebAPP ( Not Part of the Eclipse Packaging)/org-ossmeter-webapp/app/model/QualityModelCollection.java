package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class QualityModelCollection extends PongoCollection<QualityModel> {
	
	public QualityModelCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("email");
	}
	
	public Iterable<QualityModel> findById(String id) {
		return new IteratorIterable<QualityModel>(new PongoCursorIterator<QualityModel>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<QualityModel> findByIdentifier(String q) {
		return new IteratorIterable<QualityModel>(new PongoCursorIterator<QualityModel>(this, dbCollection.find(new BasicDBObject("identifier", q + ""))));
	}
	
	public QualityModel findOneByIdentifier(String q) {
		QualityModel QualityModel = (QualityModel) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("identifier", q + "")));
		if (QualityModel != null) {
			QualityModel.setPongoCollection(this);
		}
		return QualityModel;
	}
	

	public long countByIdentifier(String q) {
		return dbCollection.count(new BasicDBObject("identifier", q + ""));
	}
	
	@Override
	public Iterator<QualityModel> iterator() {
		return new PongoCursorIterator<QualityModel>(this, dbCollection.find());
	}
	
	public void add(QualityModel QualityModel) {
		super.add(QualityModel);
	}
	
	public void remove(QualityModel QualityModel) {
		super.remove(QualityModel);
	}
	
}