package org.eclipse.scava.crossflow.runtime.serializer.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.serialization.JsonSerializer;
import org.eclipse.scava.crossflow.runtime.serialization.Serializer;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonSerializerTest {

	protected static Serializer serializer;

	@BeforeClass
	public static void before() {
		serializer = new JsonSerializer(true);
		serializer.registerType(SerializationTestObject.class);
		assertTrue(serializer.getRegisteredTypes().contains(SerializationTestObject.class));
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
		SerializationTestObject dummy = new SerializationTestObject();
		dummy.stringProp = "default";
		dummy.intProp = 123;
		dummy.longProp = 123L;
		dummy.booleanProp = true;
		
		String actual = serializer.serialize(dummy);
		String expected = "{\n" + 
				"  \"stringProp\": \"default\",\n" + 
				"  \"intProp\": 123,\n" + 
				"  \"longProp\": 123,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void serialize_should_return_correct_json_string_when_given_object_with_list() throws Exception {
		SerializationTestObject parent = new SerializationTestObject();
		parent.stringProp = "parent";
		parent.listProp = new ArrayList<SerializationTestObject>();
		
		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		parent.listProp.add(child1);
		
		SerializationTestObject child2 = new SerializationTestObject();
		child2.stringProp = "child2";
		parent.listProp.add(child2);
		
		String actual = serializer.serialize(parent);
		String expected = "{\n" + 
				"  \"stringProp\": \"parent\",\n" + 
				"  \"intProp\": 0,\n" + 
				"  \"longProp\": 0,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"listProp\": [\n" + 
				"    {\n" + 
				"      \"stringProp\": \"child1\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"stringProp\": \"child2\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void serialize_should_return_correct_json_string_when_given_object_with_map() throws Exception {
		SerializationTestObject parent = new SerializationTestObject();
		parent.stringProp = "parent";
		parent.mapProp = new HashMap<String, SerializationTestObject>();
		
		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		parent.mapProp.put("child1_key", child1);
		
		SerializationTestObject child2 = new SerializationTestObject();
		child1.stringProp = "child2";
		parent.mapProp.put("child2_key", child2);
		
		String actual = serializer.serialize(parent);
		String expected = "{\n" + 
				"  \"stringProp\": \"parent\",\n" + 
				"  \"intProp\": 0,\n" + 
				"  \"longProp\": 0,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"mapProp\": {\n" + 
				"    \"child1_key\": {\n" + 
				"      \"stringProp\": \"child2\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    },\n" + 
				"    \"child2_key\": {\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    }\n" + 
				"  },\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";
		assertEquals(expected, actual);
	}
	
	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json() throws Exception {
		String json = "{\n" + 
				"  \"stringProp\": \"default\",\n" + 
				"  \"intProp\": 123,\n" + 
				"  \"longProp\": 123,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";		
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		
		SerializationTestObject expected = new SerializationTestObject();
		expected.stringProp = "default";
		expected.intProp = 123;
		expected.longProp = 123L;
		expected.booleanProp = true;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json_with_list() throws Exception {
		String json = "{\n" + 
				"  \"stringProp\": \"parent\",\n" + 
				"  \"intProp\": 0,\n" + 
				"  \"longProp\": 0,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"listProp\": [\n" + 
				"    {\n" + 
				"      \"stringProp\": \"child1\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"stringProp\": \"child2\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		
		SerializationTestObject expected = new SerializationTestObject();
		expected.stringProp = "parent";
		expected.listProp = new ArrayList<SerializationTestObject>();
		
		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		expected.listProp.add(child1);
		
		SerializationTestObject child2 = new SerializationTestObject();
		child2.stringProp = "child2";
		expected.listProp.add(child2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json_with_map() throws Exception {
		String json = "{\n" + 
				"  \"stringProp\": \"parent\",\n" + 
				"  \"intProp\": 0,\n" + 
				"  \"longProp\": 0,\n" + 
				"  \"booleanProp\": true,\n" + 
				"  \"mapProp\": {\n" + 
				"    \"child1_key\": {\n" + 
				"      \"stringProp\": \"child2\",\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    },\n" + 
				"    \"child2_key\": {\n" + 
				"      \"intProp\": 0,\n" + 
				"      \"longProp\": 0,\n" + 
				"      \"booleanProp\": true,\n" + 
				"      \"_type_\": \"SerializationTestObject\"\n" + 
				"    }\n" + 
				"  },\n" + 
				"  \"_type_\": \"SerializationTestObject\"\n" + 
				"}";
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);

		SerializationTestObject expected = new SerializationTestObject();
		expected.stringProp = "parent";
		expected.mapProp = new HashMap<String, SerializationTestObject>();
		
		SerializationTestObject child1 = new SerializationTestObject();
		child1.stringProp = "child1";
		expected.mapProp.put("child1_key", child1);
		
		SerializationTestObject child2 = new SerializationTestObject();
		child1.stringProp = "child2";
		expected.mapProp.put("child2_key", child2);
		
		assertEquals(expected, actual);
	}

}
