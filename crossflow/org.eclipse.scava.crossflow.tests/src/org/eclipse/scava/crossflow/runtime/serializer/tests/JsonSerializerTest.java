package org.eclipse.scava.crossflow.runtime.serializer.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.scava.crossflow.runtime.FailedJob;
import org.eclipse.scava.crossflow.runtime.InternalException;
import org.eclipse.scava.crossflow.runtime.Job;
import org.eclipse.scava.crossflow.runtime.LoggingStrategy;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.serialization.JsonSerializer;
import org.eclipse.scava.crossflow.runtime.serialization.Serializer;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;
import org.eclipse.scava.crossflow.runtime.utils.ControlSignal.ControlSignals;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;
import org.eclipse.scava.crossflow.runtime.utils.LogMessage;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadata;
import org.eclipse.scava.crossflow.runtime.utils.StreamMetadataSnapshot;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus.TaskStatuses;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.Files;

public class JsonSerializerTest {

	protected static Serializer serializer;

	@BeforeClass
	public static void before() {
		serializer = new JsonSerializer(true, true);
		serializer.registerType(SerializationTestObject.class);
		serializer.registerType(SerializationTestEnum.class);

		// o.e.s.c.runtime.*
		serializer.registerType(FailedJob.class);
		serializer.registerType(InternalException.class);
		serializer.registerType(Job.class);
		serializer.registerType(LoggingStrategy.class);
		serializer.registerType(Mode.class);

		// o.e.s.c.runtime.utils.*
		serializer.registerType(ControlSignal.class);
		serializer.registerType(ControlSignals.class);
		serializer.registerType(LogLevel.class);
		serializer.registerType(LogMessage.class);
		serializer.registerType(StreamMetadata.class);
		serializer.registerType(StreamMetadataSnapshot.class);
		serializer.registerType(TaskStatus.class);
		serializer.registerType(TaskStatuses.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void serialize_should_throw_iae_when_given_non_registered_object() throws Exception {
		serializer.serialize(new JsonSerializerTest());
	}

	@Test(expected = IllegalArgumentException.class)
	public void serialize_should_throw_iae_when_given_non_registered_enum() throws Exception {
		serializer.serialize(BadEnum.ERROR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deserialize_should_throw_iae_when_given_non_registered_object() throws Exception {
		serializer.deserialize("{\"blobId\":\"an-id\",\"_type_\":\"Blob\"}");
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
	public void serialize_should_return_correct_json_string_when_given_object_with_enum() throws Exception {
		SerializationTestObject obj = SerializationTestObject.getPrimitveInstance();
		obj.enumProp = SerializationTestEnum.VALUE_B;
		String actual = serializer.serialize(obj);
		String expected = jsonFromFile("SerializationTestObject-withEnum.json");
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

	@Test
	public void deserialize_should_return_SerializationTestObject_when_given_valid_json_with_enum() throws Exception {
		String json = jsonFromFile("SerializationTestObject-withEnum.json");
		SerializationTestObject actual = serializer.<SerializationTestObject>deserialize(json);
		SerializationTestObject expected = SerializationTestObject.getPrimitveInstance();
		expected.enumProp = SerializationTestEnum.VALUE_B;
		assertEquals(expected, actual);
	}

	@Test
	public void serialize_should_return_correct_json_when_given_ControlSignal_objects() throws Exception {
		final String senderId = "JsonSerializerTest Sender";
		for (ControlSignals signal : ControlSignals.values()) {
			ControlSignal obj = new ControlSignal(signal, senderId);
			String actual = serializer.serialize(obj);
			String expected = jsonFromFile("ControlSignal-" + signal.name() + ".json");
			assertEquals(expected, actual);
		}
	}

	@Test
	public void deserialize_should_return_ControlSignal_when_given_valid_json() throws Exception {
		final String senderId = "JsonSerializerTest Sender";		
		for (ControlSignals signal : ControlSignals.values()) {
			String json = jsonFromFile("ControlSignal-" + signal.name() + ".json");
			ControlSignal actual = serializer.deserialize(json);
			ControlSignal expected = new ControlSignal(signal, senderId);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void serialize_should_return_correct_json_when_given_LogMessage_objects() throws Exception {
		final long timestamp = 123456;
		final String instanceId = "JsonSerializerTest Sender";
		final String workflow = "workflow 1";
		final String task = "task a";
		final String message = "this is a message";
		
		for (LogLevel level : LogLevel.values()) {
			LogMessage logMessage = new LogMessage()
					.level(level)
					.timestamp(timestamp)
					.instanceId(instanceId)
					.workflow(workflow)
					.task(task)
					.message(message);
	
			String actual = serializer.serialize(logMessage);
			String expected = jsonFromFile("LogMessage-" + level.name() + ".json");
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void deserialize_should_return_LogMessage_when_given_valid_json() throws Exception {
		final long timestamp = 123456;
		final String instanceId = "JsonSerializerTest Sender";
		final String workflow = "workflow 1";
		final String task = "task a";
		final String message = "this is a message";
		
		for (LogLevel level : LogLevel.values()) {
			String json = jsonFromFile("LogMessage-" + level.name() + ".json");
			LogMessage actual = serializer.deserialize(json);
			LogMessage expected = new LogMessage()
					.level(level)
					.timestamp(timestamp)
					.instanceId(instanceId)
					.workflow(workflow)
					.task(task)
					.message(message);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void serialize_should_return_correct_json_when_given_TaskStatus_objects() throws Exception {
		final String caller = "JsonSerializerTest Sender";
		final String reason = "A reason string";
		
		for (TaskStatuses status : TaskStatuses.values()) {
			TaskStatus obj = new TaskStatus(status, caller, reason);
			String actual = serializer.serialize(obj);
			String expected = jsonFromFile("TaskStatus-" + status.name() + ".json");
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void deserialize_should_return_TaskStatus_when_given_valid_json() throws Exception {
		final String caller = "JsonSerializerTest Sender";
		final String reason = "A reason string";
		
		for (TaskStatuses status : TaskStatuses.values()) {
			String json = jsonFromFile("TaskStatus-" + status.name() + ".json");
			TaskStatus actual = serializer.deserialize(json);
			TaskStatus expected = new TaskStatus(status, caller, reason);
			assertEquals(expected, actual);
		}
	}

	/**
	 * Helper to retrieve pre-serialized JSON from disk
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String jsonFromFile(String file) throws IOException {
		return Files.asCharSource(new File("serialization/json/" + file), Charset.forName("UTF-8")).read();
	}

	enum BadEnum {
		ERROR;
	}
}
