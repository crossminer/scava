package com.googlecode.pongo.runtime;

import java.util.Iterator;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class PongoList<T extends Pongo> extends BasicDBListWrapper<T> {
	
	protected Pongo container;
	protected String containingFeature;
	protected boolean containment = false;
	
	public PongoList(Pongo container, String feature, boolean containment) {
		super((BasicDBList) container.dbObject.get(feature));
		this.container = container;
		this.containingFeature = feature;
		this.containment = containment;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new PongoListIterator<T>(this);
	}
	
	protected void added(T t) {
		container.notifyChanged();
		if (containment) {
			t.setContainer(container);
			t.setContainingFeature(containingFeature);
		}
	};
	
	@Override
	protected void removed(Object o) {
		container.notifyChanged();
		if (containment) {
			((Pongo) o).setContainer(null);
		}
	}
	
	@Override
	protected T wrap(Object o) {
		if (containment) {
			return (T) PongoFactory.getInstance().createPongo((DBObject) o);
		}
		else {
			return (T) PongoFactory.getInstance().resolveReference(o);
		}
	}
	
	@Override
	protected Object unwrap(Object o) {
		if (containment) {
			return ((Pongo) o).getDbObject();
		}
		else {
			return ((Pongo) o).createDBRef();
		}
	}

}
