package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class ExtensionKeywordTuple extends Job {
	
	protected String field0;
	
	public void setField0(String field0) {
		this.field0 = field0;
	}
	
	public String getField0() {
		return field0;
	}
	
	protected String field1;
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getField1() {
		return field1;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[2];
	 	ret[0] = getField0();
	 	ret[1] = getField1();
		return ret;
	}


}