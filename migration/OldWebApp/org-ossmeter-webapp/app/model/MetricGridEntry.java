package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class MetricGridEntry extends GridEntry {
	
	protected Project project = null;
	protected Metric metric = null;
	
	
	public MetricGridEntry() { 
		super();
		dbObject.put("project", new Project().getDbObject());
		dbObject.put("metric", new Metric().getDbObject());
		super.setSuperTypes("model.GridEntry");
		COL.setOwningType("model.MetricGridEntry");
		ROW.setOwningType("model.MetricGridEntry");
		SIZEX.setOwningType("model.MetricGridEntry");
		SIZEY.setOwningType("model.MetricGridEntry");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	
	
	
	
	
	
	public Project getProject() {
		if (project == null && dbObject.containsField("project")) {
			project = (Project) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("project"));
			project.setContainer(this);
		}
		return project;
	}
	
	public MetricGridEntry setProject(Project project) {
		if (this.project != project) {
			if (project == null) {
				dbObject.removeField("project");
			}
			else {
				dbObject.put("project", project.getDbObject());
			}
			this.project = project;
			notifyChanged();
		}
		return this;
	}
	public Metric getMetric() {
		if (metric == null && dbObject.containsField("metric")) {
			metric = (Metric) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("metric"));
			metric.setContainer(this);
		}
		return metric;
	}
	
	public MetricGridEntry setMetric(Metric metric) {
		if (this.metric != metric) {
			if (metric == null) {
				dbObject.removeField("metric");
			}
			else {
				dbObject.put("metric", metric.getDbObject());
			}
			this.metric = metric;
			notifyChanged();
		}
		return this;
	}
}