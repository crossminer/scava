package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Notification extends MetricGridEntry {
	
	
	
	public Notification() { 
		super();
		super.setSuperTypes("model.MetricGridEntry","model.GridEntry");
		COL.setOwningType("model.Notification");
		ROW.setOwningType("model.Notification");
		SIZEX.setOwningType("model.Notification");
		SIZEY.setOwningType("model.Notification");
		THRESHOLD.setOwningType("model.Notification");
		ABOVETHRESHOLD.setOwningType("model.Notification");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static NumericalQueryProducer THRESHOLD = new NumericalQueryProducer("threshold");
	public static StringQueryProducer ABOVETHRESHOLD = new StringQueryProducer("aboveThreshold"); 
	
	
	public double getThreshold() {
		return parseDouble(dbObject.get("threshold")+"", 0.0d);
	}
	
	public Notification setThreshold(double threshold) {
		dbObject.put("threshold", threshold);
		notifyChanged();
		return this;
	}
	public boolean getAboveThreshold() {
		return parseBoolean(dbObject.get("aboveThreshold")+"", false);
	}
	
	public Notification setAboveThreshold(boolean aboveThreshold) {
		dbObject.put("aboveThreshold", aboveThreshold);
		notifyChanged();
		return this;
	}
	
	
	
	
}