package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class QualityMetric extends QualityElement {
	
	
	
	public QualityMetric() { 
		super();
		super.setSuperTypes("model.QualityElement");
		IDENTIFIER.setOwningType("model.QualityMetric");
		NAME.setOwningType("model.QualityMetric");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}