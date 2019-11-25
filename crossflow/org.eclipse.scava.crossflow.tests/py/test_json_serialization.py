from enum import Enum, auto
import json
import os
from pathlib import Path
import unittest

from crossflow import serialization
from crossflow.runtime import (
    LogMessage,
    TaskStatus,
    TaskStatuses,
    ControlSignals,
    LogLevel,
    ControlSignal,
    Mode,
    Job,
    InternalException,
    FailedJob,
)
from symbol import try_stmt, except_clause
from builtins import ValueError


def json_from_file(file: str) -> str:
    with open(
        os.path.join(
            Path(os.path.dirname(os.path.realpath(__file__))),
            "../serialization/json",
            file,
        )
    ) as f:
        return f.read()


class SerializationTestEnum(Enum):
    VALUE_A = auto()
    VALUE_B = auto()


class SerializationTestObject:
    def __init__(self):
        self.stringProp: str = None
        self.intProp: int = 0
        self.longProp: int = 0
        self.booleanProp: bool = True
        self.listProp: list = []
        self.mapProp: dict = {}
        self.enumProp: SerializationTestEnum = None

    def __eq__(self, other):
        if isinstance(other, SerializationTestObject):
            return (
                self.stringProp == other.stringProp
                and self.intProp == other.intProp
                and self.longProp == other.longProp
                and self.booleanProp == other.booleanProp
                and self.listProp == other.listProp
                and self.mapProp == other.mapProp
                and self.enumProp == other.enumProp
            )
        else:
            return False

    @staticmethod
    def get_primitive_instance():
        sto = SerializationTestObject()
        sto.stringProp = "default"
        sto.intProp = 123
        sto.longProp = 123
        sto.booleanProp = True
        return sto

    @staticmethod
    def get_list_instance():
        parent = SerializationTestObject()
        parent.stringProp = "parent"
        parent.listProp = []

        child_1 = SerializationTestObject()
        child_1.stringProp = "child1"
        parent.listProp.append(child_1)

        child_2 = SerializationTestObject()
        child_2.stringProp = "child2"
        parent.listProp.append(child_2)

        return parent

    @staticmethod
    def get_map_instance():
        parent = SerializationTestObject()
        parent.stringProp = "parent"

        child_1 = SerializationTestObject()
        child_1.stringProp = "child1"
        parent.mapProp["child1_key"] = child_1

        child_2 = SerializationTestObject()
        child_2.stringProp = "child2"
        parent.mapProp["child2_key"] = child_2

        return parent


class TestSerializerUtils(unittest.TestCase):
    def test_to_type(self):
        self.assertEqual(serialization._to_type("a string"), str)
        self.assertEqual(serialization._to_type(str), str)

    def test_sanitize_key(self):
        self.assertEqual(serialization._sanitize_key("akey"), "akey")
        self.assertEqual(serialization._sanitize_key("_a_key"), "aKey")
        self.assertEqual(serialization._sanitize_key("__a_key"), "__a_key")


class TestJsonSerializer(unittest.TestCase):
    def assertJsonEqual(self, first, second, msg=None):
        f = json.loads(first)
        s = json.loads(second)
        self.assertEqual(json.loads(first), json.loads(second), msg)

    def setUp(self):
        self.serializer = serialization.JsonSerializer()
        self.serializer.register_type(SerializationTestObject)
        self.serializer.register_type(SerializationTestEnum)

        # TODO: commented out types are currently only available in Java
        self.serializer.register_type(FailedJob)
        self.serializer.register_type(InternalException)
        self.serializer.register_type(Job)
        # self.serializer.register_type(LoggingStrategy)
        self.serializer.register_type(Mode)

        self.serializer.register_type(ControlSignal)
        self.serializer.register_type(ControlSignals)
        self.serializer.register_type(LogLevel)
        self.serializer.register_type(LogMessage)
        # self.serializer.register_type(StreamMetadata)
        # self.serializer.register_type(StreamMetadataSnapshot)
        self.serializer.register_type(TaskStatus)
        self.serializer.register_type(TaskStatuses)

    def test_serialize_should_return_correct_json_string_when_given_registered_type_with_just_primitives(
        self
    ):
        actual = self.serializer.serialize(
            SerializationTestObject.get_primitive_instance()
        )
        expected = json_from_file("SerializationTestObject-primitives.json")
        self.assertJsonEqual(actual, expected)

    def test_serialize_should_return_correct_json_string_when_given_object_with_list(
        self
    ):
        actual = self.serializer.serialize(SerializationTestObject.get_list_instance())
        expected = json_from_file("SerializationTestObject-list.json")
        self.assertJsonEqual(actual, expected)

    def test_serialize_should_return_correct_json_string_when_given_object_with_map(
        self
    ):
        actual = self.serializer.serialize(SerializationTestObject.get_map_instance())
        expected = json_from_file("SerializationTestObject-map.json")
        self.assertJsonEqual(actual, expected)

    def test_serialize_should_return_correct_json_string_when_given_object_with_enum(
        self
    ):
        obj = SerializationTestObject.get_primitive_instance()
        obj.enumProp = SerializationTestEnum.VALUE_B
        actual = self.serializer.serialize(obj)
        expected = json_from_file("SerializationTestObject-withEnum.json")
        self.assertJsonEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json(
        self
    ):
        json_str = json_from_file("SerializationTestObject-primitives.json")
        actual = self.serializer.deserialize(json_str)
        expected = SerializationTestObject.get_primitive_instance()
        self.assertEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json_with_list(
        self
    ):
        json_str = json_from_file("SerializationTestObject-list.json")
        actual = self.serializer.deserialize(json_str)
        expected = SerializationTestObject.get_list_instance()
        self.assertEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json_with_map(
        self
    ):
        json_str = json_from_file("SerializationTestObject-map.json")
        actual = self.serializer.deserialize(json_str)
        expected = SerializationTestObject.get_map_instance()
        self.assertEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json_with_enum(
        self
    ):
        json_str = json_from_file("SerializationTestObject-withEnum.json")
        actual = self.serializer.deserialize(json_str)
        expected = SerializationTestObject.get_primitive_instance()
        expected.enumProp = SerializationTestEnum.VALUE_B
        self.assertEqual(actual, expected)

    def test_serialize_should_return_correct_json_when_given_ControlSignal_objects(
        self
    ):
        senderId = "JsonSerializerTest Sender"
        for signal in ControlSignals:
            with self.subTest():
                obj = ControlSignal(signal=signal, sender_id=senderId)
                actual = self.serializer.serialize(obj)
                expected = json_from_file(f"ControlSignal-{signal.name}.json")
                self.assertJsonEqual(actual, expected)

    def test_deserialize_should_return_ControlSignal_when_given_valid_json(self):
        senderId = "JsonSerializerTest Sender"
        for signal in ControlSignals:
            with self.subTest():
                json_str = json_from_file(f"ControlSignal-{signal.name}.json")
                actual = self.serializer.deserialize(json_str)
                expected = ControlSignal(signal=signal, sender_id=senderId)
                self.assertEqual(actual, expected)

    def test_serialize_should_return_correct_json_when_given_LogMessage_objects(self):
        timestamp = 123456
        instance_id = "JsonSerializerTest Sender"
        workflow = "workflow 1"
        task = "task a"
        message = "this is a message"

        for level in LogLevel:
            with self.subTest():
                logMessage = LogMessage(
                    level=level,
                    instance_id=instance_id,
                    workflow=workflow,
                    task=task,
                    message=message,
                    timestamp=timestamp,
                )
                actual = self.serializer.serialize(logMessage)
                expected = json_from_file(f"LogMessage-{level.name}.json")
                self.assertJsonEqual(actual, expected)

    def test_deserialize_should_return_LogMessage_when_given_valid_json(self):
        timestamp = 123456
        instance_id = "JsonSerializerTest Sender"
        workflow = "workflow 1"
        task = "task a"
        message = "this is a message"

        for level in LogLevel:
            with self.subTest():
                json_str = json_from_file(f"LogMessage-{level.name}.json")
                actual = self.serializer.deserialize(json_str)
                expected = LogMessage(
                    level=level,
                    instance_id=instance_id,
                    workflow=workflow,
                    task=task,
                    message=message,
                    timestamp=timestamp,
                )
                self.assertEqual(actual, expected)

    def test_serialize_should_return_correct_json_when_given_TaskStatus_objects(self):
        caller = "JsonSerializerTest Sender"
        reason = "A reason string"

        for status in TaskStatuses:
            with self.subTest():
                obj = TaskStatus(status, caller, reason)
                actual = self.serializer.serialize(obj)
                expected = json_from_file(f"TaskStatus-{status.name}.json")
                self.assertJsonEqual(actual, expected)

    def test_deserialize_should_return_TaskStatus_when_given_valid_json(self):
        caller = "JsonSerializerTest Sender"
        reason = "A reason string"

        for status in TaskStatuses:
            with self.subTest():
                json_str = json_from_file(f"TaskStatus-{status.name}.json")
                actual = self.serializer.deserialize(json_str)
                expected = TaskStatus(status, caller, reason)
                self.assertEqual(actual, expected)

    def test_serialize_should_return_correct_json_when_given_python_InternalException_objects(
        self
    ):
        # Construct nested exception
        exception = ValueError("A value error message")
        exception.__cause__ = ValueError("A nested value error message")

        sender_id = "JsonSerializerTest Sender"
        internal_exception = InternalException.from_exception(exception, sender_id)
        actual = self.serializer.serialize(internal_exception)
        expected = json_from_file("InternalException-python.json")
        self.assertJsonEqual(actual, expected)

    def test_deserialize_should_return_InternalException_when_given_valid_json_for_python_exception(
        self
    ):
        json_str = json_from_file("InternalException-python.json")
        actual = self.serializer.deserialize(json_str)
        reason = "ValueError: A value error message"
        stacktrace = "ValueError: A nested value error message\n\nThe above exception was the direct cause of the following exception:\n\nValueError: A value error message"
        sender_id = "JsonSerializerTest Sender"
        self.assertEqual(actual.reason, reason)
        self.assertEqual(actual.stacktrace, stacktrace)
        self.assertEqual(actual.sender_id, sender_id)
