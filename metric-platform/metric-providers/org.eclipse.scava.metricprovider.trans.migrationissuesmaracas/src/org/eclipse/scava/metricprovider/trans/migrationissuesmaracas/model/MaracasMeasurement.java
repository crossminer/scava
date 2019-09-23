package org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MaracasMeasurement extends Pongo {
	
	protected List<String> regex = null;
	
	
	public MaracasMeasurement() { 
		super();
		dbObject.put("regex", new BasicDBList());
		REGEX.setOwningType("org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MaracasMeasurement");
		CHANGE.setOwningType("org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MaracasMeasurement");
		LASTUPDATEDATE.setOwningType("org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MaracasMeasurement");
	}
	
	public static StringQueryProducer CHANGE = new StringQueryProducer("change"); 
	public static NumericalQueryProducer LASTUPDATEDATE = new NumericalQueryProducer("lastUpdateDate");
	public static ArrayQueryProducer REGEX = new ArrayQueryProducer("regex");
	
	
	public String getChange() {
		return parseString(dbObject.get("change")+"", "");
	}
	
	public MaracasMeasurement setChange(String change) {
		dbObject.put("change", change);
		notifyChanged();
		return this;
	}
	public int getLastUpdateDate() {
		return parseInteger(dbObject.get("lastUpdateDate")+"", 0);
	}
	
	public MaracasMeasurement setLastUpdateDate(int lastUpdateDate) {
		dbObject.put("lastUpdateDate", lastUpdateDate);
		notifyChanged();
		return this;
	}
	
	public List<String> getRegex() {
		if (regex == null) {
			regex = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("regex"));
		}
		return regex;
	}
	
	
	
}