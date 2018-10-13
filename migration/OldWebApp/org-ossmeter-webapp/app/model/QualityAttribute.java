package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class QualityAttribute extends QualityElement {
	
	protected List<QualityMetric> factoids = null;
	protected List<QualityMetric> metrics = null;
	
	
	public QualityAttribute() { 
		super();
		dbObject.put("factoids", new BasicDBList());
		dbObject.put("metrics", new BasicDBList());
		super.setSuperTypes("model.QualityElement");
		IDENTIFIER.setOwningType("model.QualityAttribute");
		NAME.setOwningType("model.QualityAttribute");
		DESCRIPTION.setOwningType("model.QualityAttribute");
		DETAIL.setOwningType("model.QualityAttribute");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer DETAIL = new StringQueryProducer("detail"); 
	
	
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public QualityAttribute setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getDetail() {
		return parseString(dbObject.get("detail")+"", "");
	}
	
	public QualityAttribute setDetail(String detail) {
		dbObject.put("detail", detail);
		notifyChanged();
		return this;
	}
	
	
	public List<QualityMetric> getFactoids() {
		if (factoids == null) {
			factoids = new PongoList<QualityMetric>(this, "factoids", true);
		}
		return factoids;
	}
	public List<QualityMetric> getMetrics() {
		if (metrics == null) {
			metrics = new PongoList<QualityMetric>(this, "metrics", true);
		}
		return metrics;
	}
	
	
}