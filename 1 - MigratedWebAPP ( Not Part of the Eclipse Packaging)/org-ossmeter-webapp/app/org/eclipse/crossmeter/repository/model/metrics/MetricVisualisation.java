package org.eclipse.crossmeter.repository.model.metrics;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.eclipse.crossmeter.platform.factoids.*;
import org.eclipse.crossmeter.repository.model.*;
import org.eclipse.crossmeter.repository.model.bts.bugzilla.*;
import org.eclipse.crossmeter.repository.model.cc.forum.*;
import org.eclipse.crossmeter.repository.model.cc.nntp.*;
import org.eclipse.crossmeter.repository.model.cc.wiki.*;
import org.eclipse.crossmeter.repository.model.eclipse.*;
import org.eclipse.crossmeter.repository.model.github.*;
import org.eclipse.crossmeter.repository.model.googlecode.*;
import org.eclipse.crossmeter.repository.model.metrics.*;
import org.eclipse.crossmeter.repository.model.redmine.*;
import org.eclipse.crossmeter.repository.model.sourceforge.*;
import org.eclipse.crossmeter.repository.model.vcs.cvs.*;
import org.eclipse.crossmeter.repository.model.vcs.git.*;
import org.eclipse.crossmeter.repository.model.vcs.svn.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricVisualisation.class, name="org.eclipse.crossmeter.repository.model.metrics.MetricVisualisation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricVisualisation extends Object {

	protected String _id;
	protected String type;
	protected String description;
	protected String x;
	protected String y;
	protected Datatable datatable;
	
	public String get_id() {
		return _id;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getX() {
		return x;
	}
	public String getY() {
		return y;
	}
	
}
