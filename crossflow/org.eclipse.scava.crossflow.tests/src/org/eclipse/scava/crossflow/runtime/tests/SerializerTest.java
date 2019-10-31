package org.eclipse.scava.crossflow.runtime.tests;

import static org.junit.Assert.*;

import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.Serializer;
import org.junit.Before;
import org.junit.Test;

public class SerializerTest {
	
	protected Serializer serializer;
	
	protected String jobXml = "<Job>\n" + 
			"  <jobId>0</jobId>\n" + 
			"  <cached>false</cached>\n" + 
			"  <failures>0</failures>\n" + 
			"  <cacheable>true</cacheable>\n" + 
			"  <timeout>0</timeout>\n" +
			"  <transactional>true</transactional>\n" + 
			"  <isTransactionSuccessMessage>false</isTransactionSuccessMessage>\n" + 
			"  <totalOutputs>0</totalOutputs>\n" + 
			"</Job>";

	@Before
	public void before() {
		serializer = new Serializer();
		serializer.register(Job.class);
	}

	@Test
	public void toString_should_return_short_name_when_given_Job() throws Exception {
		serializer.register(Job.class);
		Job job = new Job();
		job.setJobId("0");
		String xml = serializer.toString(job);
		assertEquals(jobXml, xml);
	}
	
	@Test
	public void toObject_should_return_correct_Job_when_given_aliased_output() throws Exception {
		Object object = serializer.toObject(jobXml);
		assertTrue(object instanceof Job);
		Job job = (Job) object;
		
		assertEquals("0", job.getJobId());
		assertFalse(job.isCached());
		assertEquals(0, job.getFailures());
		assertTrue(job.isTransactional());
		assertFalse(job.isTransactionSuccessMessage());
		assertEquals(0, job.getTotalOutputs());
	}

}
