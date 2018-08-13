package org.eclipse.scava.platform.analysis.data.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SchedulingInformation extends Pongo {
	
	
	
	public SchedulingInformation() { 
		super();
		STATUS.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		CURRENTDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		HEARTBEAT.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		WORKERID.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		PROGRESS.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		CURRENTMETRIC.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
	}
	
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer CURRENTDATE = new StringQueryProducer("currentDate"); 
	public static StringQueryProducer HEARTBEAT = new StringQueryProducer("heartbeat"); 
	public static StringQueryProducer WORKERID = new StringQueryProducer("workerId"); 
	public static NumericalQueryProducer PROGRESS = new NumericalQueryProducer("progress");
	public static StringQueryProducer CURRENTMETRIC = new StringQueryProducer("currentMetric"); 
	
	
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public SchedulingInformation setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public Date getCurrentDate() {
		return parseDate(dbObject.get("currentDate")+"", null);
	}
	
	public SchedulingInformation setCurrentDate(Date currentDate) {
		dbObject.put("currentDate", currentDate);
		notifyChanged();
		return this;
	}
	public Date getHeartbeat() {
		return parseDate(dbObject.get("heartbeat")+"", null);
	}
	
	public SchedulingInformation setHeartbeat(Date heartbeat) {
		dbObject.put("heartbeat", heartbeat);
		notifyChanged();
		return this;
	}
	public String getWorkerId() {
		return parseString(dbObject.get("workerId")+"", "");
	}
	
	public SchedulingInformation setWorkerId(String workerId) {
		dbObject.put("workerId", workerId);
		notifyChanged();
		return this;
	}
	public int getProgress() {
		return parseInteger(dbObject.get("progress")+"", 0);
	}
	
	public SchedulingInformation setProgress(int progress) {
		dbObject.put("progress", progress);
		notifyChanged();
		return this;
	}
	public String getCurrentMetric() {
		return parseString(dbObject.get("currentMetric")+"", "");
	}
	
	public SchedulingInformation setCurrentMetric(String currentMetric) {
		dbObject.put("currentMetric", currentMetric);
		notifyChanged();
		return this;
	}
	
	
	
	
}