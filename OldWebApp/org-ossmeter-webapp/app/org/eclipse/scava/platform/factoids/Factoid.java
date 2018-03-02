package org.eclipse.scava.platform.factoids;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.eclipse.scava.platform.factoids.*;
import org.eclipse.scava.repository.model.*;
import org.eclipse.scava.repository.model.bts.bugzilla.*;
import org.eclipse.scava.repository.model.cc.forum.*;
import org.eclipse.scava.repository.model.cc.nntp.*;
import org.eclipse.scava.repository.model.cc.wiki.*;
import org.eclipse.scava.repository.model.eclipse.*;
import org.eclipse.scava.repository.model.github.*;
import org.eclipse.scava.repository.model.googlecode.*;
import org.eclipse.scava.repository.model.metrics.*;
import org.eclipse.scava.repository.model.redmine.*;
import org.eclipse.scava.repository.model.sourceforge.*;
import org.eclipse.scava.repository.model.vcs.cvs.*;
import org.eclipse.scava.repository.model.vcs.git.*;
import org.eclipse.scava.repository.model.vcs.svn.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Factoid.class, name="org.eclipse.scava.platform.factoids.Factoid"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Factoid extends Object {

	protected List<String> metricDependencies;
	protected String metricId;
	protected String factoid;
	protected StarRating stars;
	protected FactoidCategory category;
	
	public String getMetricId() {
		return metricId;
	}
	public String getFactoid() {
		return factoid;
	}
	public StarRating getStars() {
		return stars;
	}
	public FactoidCategory getCategory() {
		return category;
	}
	
	public List<String> getMetricDependencies() {
		return metricDependencies;
	}
}
