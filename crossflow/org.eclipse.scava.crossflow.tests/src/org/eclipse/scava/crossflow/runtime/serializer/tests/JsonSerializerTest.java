package org.eclipse.scava.crossflow.runtime.serializer.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.serialization.JsonSerializer;
import org.eclipse.scava.crossflow.runtime.serialization.Serializer;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonSerializerTest {

	protected static Serializer serializer;

	@BeforeClass
	public static void before() {
		serializer = new JsonSerializer();
		serializer.registerType(DummyObject.class);
		assertTrue(serializer.getRegisteredTypes().contains(DummyObject.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void serialize_should_throw_iae_when_given_non_registered_object() throws Exception {
		serializer.serialize(new Job());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deserialize_should_throw_iae_when_given_non_registered_object() throws Exception {
		serializer.deserialize("{\"jobId\":\"an-id\",\"_type_\":\"Job\"}");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deserialize_should_throw_iae_when_given_object_with_no_type_field() throws Exception {
		serializer.deserialize("{\"jobId\":\"an-id\"}");
	}

	@Test
	public void serialize_should_return_correct_json_string_when_given_registered_type_with_just_primitives() throws Exception {
		DummyObject dummy = new DummyObject();
		dummy.stringProp = "default";
		dummy.intProp = 123;
		dummy.longProp = 123L;
		dummy.booleanProp = true;
		
		String actual = serializer.serialize(dummy);
		String expected = "{\"stringProp\":\"default\",\"intProp\":123,\"longProp\":123,\"booleanProp\":true,\"_type_\":\"DummyObject\"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void serialize_should_return_correct_json_string_when_given_object_with_empty_collections() throws Exception {
		DummyObject parent = new DummyObject();
		parent.stringProp = "parent";
		parent.listProp = new ArrayList<DummyObject>();
		
		DummyObject child1 = new DummyObject();
		child1.stringProp = "child1";
		parent.listProp.add(child1);
		
		DummyObject child2 = new DummyObject();
		child2.stringProp = "child2";
		parent.listProp.add(child2);
		
		String actual = serializer.serialize(parent);
		String expected = "{\"stringProp\":\"parent\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"listProp\":[{\"stringProp\":\"child1\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"_type_\":\"DummyObject\"},{\"stringProp\":\"child2\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"_type_\":\"DummyObject\"}],\"_type_\":\"DummyObject\"}";
		assertEquals(actual, expected);
	}
	
	@Test
	public void deserialize_should_return_DummyObject_when_given_valid_json() throws Exception {
		String json = "{\"stringProp\":\"default\",\"intProp\":123,\"longProp\":123,\"booleanProp\":true,\"_type_\":\"DummyObject\"}";
		DummyObject actual = serializer.<DummyObject>deserialize(json);
		DummyObject expected = new DummyObject();
		expected.stringProp = "default";
		expected.intProp = 123;
		expected.longProp = 123L;
		expected.booleanProp = true;
		assertEquals(actual, expected);
	}
	
	@Test
	public void deserialize_should_return_DummyObject_when_given_valid_json_with_collections() throws Exception {
		String json = "{\"stringProp\":\"parent\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"listProp\":[{\"stringProp\":\"child1\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"_type_\":\"DummyObject\"},{\"stringProp\":\"child2\",\"intProp\":0,\"longProp\":0,\"booleanProp\":true,\"_type_\":\"DummyObject\"}],\"_type_\":\"DummyObject\"}";
		DummyObject actual = serializer.<DummyObject>deserialize(json);
		
		DummyObject expected = new DummyObject();
		expected.stringProp = "parent";
		expected.listProp = new ArrayList<DummyObject>();
		
		DummyObject child1 = new DummyObject();
		child1.stringProp = "child1";
		expected.listProp.add(child1);
		
		DummyObject child2 = new DummyObject();
		child2.stringProp = "child2";
		expected.listProp.add(child2);
		
		assertEquals(expected, actual);
	}
}
