package org.eclipse.scava.metricprovider.historic.bugs.responsetime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugsResponseTimeHistoricMetric extends Pongo {
	
	
	
	public BugsResponseTimeHistoricMetric() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		AVGRESPONSETIME.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		AVGRESPONSETIMEFORMATTED.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		BUGSCONSIDERED.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		CUMULATIVEAVGRESPONSETIME.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		CUMULATIVEAVGRESPONSETIMEFORMATTED.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
		CUMULATIVEBUGSCONSIDERED.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer AVGRESPONSETIME = new NumericalQueryProducer("avgResponseTime");
	public static StringQueryProducer AVGRESPONSETIMEFORMATTED = new StringQueryProducer("avgResponseTimeFormatted"); 
	public static NumericalQueryProducer BUGSCONSIDERED = new NumericalQueryProducer("bugsConsidered");
	public static NumericalQueryProducer CUMULATIVEAVGRESPONSETIME = new NumericalQueryProducer("cumulativeAvgResponseTime");
	public static StringQueryProducer CUMULATIVEAVGRESPONSETIMEFORMATTED = new StringQueryProducer("cumulativeAvgResponseTimeFormatted"); 
	public static NumericalQueryProducer CUMULATIVEBUGSCONSIDERED = new NumericalQueryProducer("cumulativeBugsConsidered");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugsResponseTimeHistoricMetric setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public double getAvgResponseTime() {
		return parseDouble(dbObject.get("avgResponseTime")+"", 0.0d);
	}
	
	public BugsResponseTimeHistoricMetric setAvgResponseTime(double avgResponseTime) {
		dbObject.put("avgResponseTime", avgResponseTime);
		notifyChanged();
		return this;
	}
	public String getAvgResponseTimeFormatted() {
		return parseString(dbObject.get("avgResponseTimeFormatted")+"", "");
	}
	
	public BugsResponseTimeHistoricMetric setAvgResponseTimeFormatted(String avgResponseTimeFormatted) {
		dbObject.put("avgResponseTimeFormatted", avgResponseTimeFormatted);
		notifyChanged();
		return this;
	}
	public int getBugsConsidered() {
		return parseInteger(dbObject.get("bugsConsidered")+"", 0);
	}
	
	public BugsResponseTimeHistoricMetric setBugsConsidered(int bugsConsidered) {
		dbObject.put("bugsConsidered", bugsConsidered);
		notifyChanged();
		return this;
	}
	public double getCumulativeAvgResponseTime() {
		return parseDouble(dbObject.get("cumulativeAvgResponseTime")+"", 0.0d);
	}
	
	public BugsResponseTimeHistoricMetric setCumulativeAvgResponseTime(double cumulativeAvgResponseTime) {
		dbObject.put("cumulativeAvgResponseTime", cumulativeAvgResponseTime);
		notifyChanged();
		return this;
	}
	public String getCumulativeAvgResponseTimeFormatted() {
		return parseString(dbObject.get("cumulativeAvgResponseTimeFormatted")+"", "");
	}
	
	public BugsResponseTimeHistoricMetric setCumulativeAvgResponseTimeFormatted(String cumulativeAvgResponseTimeFormatted) {
		dbObject.put("cumulativeAvgResponseTimeFormatted", cumulativeAvgResponseTimeFormatted);
		notifyChanged();
		return this;
	}
	public int getCumulativeBugsConsidered() {
		return parseInteger(dbObject.get("cumulativeBugsConsidered")+"", 0);
	}
	
	public BugsResponseTimeHistoricMetric setCumulativeBugsConsidered(int cumulativeBugsConsidered) {
		dbObject.put("cumulativeBugsConsidered", cumulativeBugsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}