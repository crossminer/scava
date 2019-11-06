import json
import os
import unittest

from crossflow import serialization


def json_from_file(file: str) -> str:
    return open(os.path.join(os.getcwd(), "..", "serialization", file)).read()


class SerializationTestObject:
    def __init__(self):
        self.stringProp: str = None
        self.intProp: int = 0
        self.longProp: int = 0
        self.booleanProp: bool = True
        self.listProp: list = []
        self.mapProp: dict = {}

    def __eq__(self, other):
        return (
            (
                self.stringProp == other.stringProp
                and self.intProp == other.intProp
                and self.longProp == other.longProp
                and self.booleanProp == other.booleanProp
                and self.listProp == other.listProp
                and self.mapProp == other.mapProp
            )
            if isinstance(other, SerializationTestObject)
            else False
        )

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
        self.assertEqual(json.loads(first), json.loads(second), msg)

    def setUp(self):
        self.serializer = serialization.JsonSerializer()
        self.serializer.register_type(SerializationTestObject)
        self.assertTrue(self.serializer.is_registered(SerializationTestObject))

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

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json(
        self
    ):
        json = json_from_file("SerializationTestObject-primitives.json")
        actual = self.serializer.deserialize(json)
        expected = SerializationTestObject.get_primitive_instance()
        self.assertEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json_with_list(
        self
    ):
        json = json_from_file("SerializationTestObject-list.json")
        actual = self.serializer.deserialize(json)
        expected = SerializationTestObject.get_list_instance()
        self.assertEqual(actual, expected)

    def test_deserialize_should_return_SerializationTestObject_when_given_valid_json_with_map(
        self
    ):
        json = json_from_file("SerializationTestObject-map.json")
        actual = self.serializer.deserialize(json)
        expected = SerializationTestObject.get_map_instance()
        self.assertEqual(actual, expected)
