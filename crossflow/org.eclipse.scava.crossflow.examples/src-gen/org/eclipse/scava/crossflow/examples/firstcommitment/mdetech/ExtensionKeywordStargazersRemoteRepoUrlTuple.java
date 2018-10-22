package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;

public class ExtensionKeywordStargazersRemoteRepoUrlTuple extends Job {
	
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
	
	protected Integer field2;
	
	public void setField2(Integer field2) {
		this.field2 = field2;
	}
	
	public Integer getField2() {
		return field2;
	}
	
	protected String field3;
	
	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	public String getField3() {
		return field3;
	}
	
	
	public Object[] toObjectArray(){
		Object[] ret = new Object[4];
	 	ret[0] = getField0();
	 	ret[1] = getField1();
	 	ret[2] = getField2();
	 	ret[3] = getField3();
		return ret;
	}


}