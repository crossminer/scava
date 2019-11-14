package org.eclipse.scava.crossflow.runtime.serializer.tests;

import static org.junit.Assert.*;

import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.serialization.Serializer;
import org.eclipse.scava.crossflow.runtime.serialization.XstreamSerializer;
import org.junit.Before;
import org.junit.Test;

public class XstreamSerializerTest {
	
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
		serializer = new XstreamSerializer();
		serializer.registerType(Job.class);
	}

	@Test
	public void serialize_should_return_short_name_when_given_Job() throws Exception {
		serializer.registerType(Job.class);
		Job job = new Job();
		job.setJobId("0");
		String xml = serializer.serialize(job);
		assertEquals(jobXml, xml);
	}
	
	@Test
	public void deserialize_should_return_correct_Job_when_given_aliased_output() throws Exception {
		Object object = serializer.deserialize(jobXml);
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
