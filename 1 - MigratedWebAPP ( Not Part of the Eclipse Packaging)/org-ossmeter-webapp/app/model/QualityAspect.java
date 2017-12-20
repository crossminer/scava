package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class QualityAspect extends QualityElement {
	
	protected List<QualityAttribute> attributes = null;
	protected List<QualityAspect> aspects = null;
	
	
	public QualityAspect() { 
		super();
		dbObject.put("attributes", new BasicDBList());
		dbObject.put("aspects", new BasicDBList());
		super.setSuperTypes("models.QualityElement");
		IDENTIFIER.setOwningType("models.QualityAspect");
		NAME.setOwningType("models.QualityAspect");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	public List<QualityAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new PongoList<QualityAttribute>(this, "attributes", true);
		}
		return attributes;
	}
	public List<QualityAspect> getAspects() {
		if (aspects == null) {
			aspects = new PongoList<QualityAspect>(this, "aspects", true);
		}
		return aspects;
	}
	
	
}