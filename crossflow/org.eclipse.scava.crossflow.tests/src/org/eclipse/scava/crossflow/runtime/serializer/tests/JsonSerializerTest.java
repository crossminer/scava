package org.eclipse.scava.crossflow.runtime.serializer.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.serialization.JsonSerializer;
import org.eclipse.scava.crossflow.runtime.serialization.Serializer;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Files;

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
	public void serialize_should_return_correct_json_string_when_given_registered_type_with_just_primitives()
			throws Exception {
		String actual = serializer.serialize(SerializationTestObject.getPrimitveInstance());
		String expected = jsonFromFile("SerializationTestObject-primitives.json");
		assertEquals(expected, actual);
	}

	@Test
	public void serialize_should_return_correct_json_string_when_given_object_with_list() throws Exception {
		String actual = serializer.serialize(SerializationTestObject.getListInstance());
		String expected = jsonFromFile("SerializationTestObject-list.json");
		assertEquals(expected, actual);
	}

	@Test
	public void serialize_should_return_correct_json_string_when_given_object_with_map() throws Exception {
		String actual = serializer.serialize(SerializationTestObject.getMapInstance());
		String expected = jsonFromFile("SerializationTestObject-map.json");
		assertEquals(expected, actual);
	}

	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json() throws Exception {
		String json = jsonFromFile("SerializationTestObject-primitives.json");
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		SerializationTestObject expected = SerializationTestObject.getPrimitveInstance();
		assertEquals(expected, actual);
	}

	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json_with_list() throws Exception {
		String json = jsonFromFile("SerializationTestObject-list.json");
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		SerializationTestObject expected = SerializationTestObject.getListInstance();
		assertEquals(expected, actual);
	}

	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json_with_map() throws Exception {
		String json = jsonFromFile("SerializationTestObject-map.json");
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		SerializationTestObject expected = SerializationTestObject.getMapInstance();
		assertEquals(expected, actual);
	}

	String jsonFromFile(String file) throws IOException {
		return Files.asCharSource(new File("serialization/" + file), Charset.forName("UTF-8")).read();
	}
}
