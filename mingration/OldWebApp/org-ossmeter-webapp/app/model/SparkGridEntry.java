package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SparkGridEntry extends MetricGridEntry {
	
	
	
	public SparkGridEntry() { 
		super();
		super.setSuperTypes("model.MetricGridEntry","model.GridEntry");
		COL.setOwningType("model.SparkGridEntry");
		ROW.setOwningType("model.SparkGridEntry");
		SIZEX.setOwningType("model.SparkGridEntry");
		SIZEY.setOwningType("model.SparkGridEntry");
		LASTVALUE.setOwningType("model.SparkGridEntry");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static NumericalQueryProducer LASTVALUE = new NumericalQueryProducer("lastValue");
	
	
	public double getLastValue() {
		return parseDouble(dbObject.get("lastValue")+"", 0.0d);
	}
	
	public SparkGridEntry setLastValue(double lastValue) {
		dbObject.put("lastValue", lastValue);
		notifyChanged();
		return this;
	}
	
	
	
	
}