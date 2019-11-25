from abc import ABC, abstractmethod
import enum
import json
import traceback
from typing import Type

import string_utils
import xmltodict

_TYPE_PROPERTY_KEY = "_type_"
_ENUM_TYPE_PROPERTY_KEY = "_enum_type_"
_ENUM_VALUE_PROPERTY_KEY = "_enum_value_"


def _to_type(type_):
    return type_ if isinstance(type_, type) else type(type_)


def _is_empty(obj) -> bool:
    if obj is None:
        return True
    if isinstance(obj, (list, dict)):
        return not obj
    else:
        return False


def _sanitize_key(key: str) -> str:
    if not isinstance(key, str):
        return key
    elif key.startswith("__"):
        return key
    elif key.startswith("_"):
        return string_utils.snake_case_to_camel(key[1:], upper_case_first=False)
    else:
        return key


def _find_correct_key(key: str, obj: object):
    def _find(key: str, obj: object, done=False):
        if isinstance(key, str):
            if hasattr(obj, key):
                return key
            if hasattr(obj, f"_{key}"):
                return f"_{key}"
            if hasattr(obj, f"__{key}"):
                return f"__{key}"
            if not done:
                return _find(string_utils.camel_case_to_snake(key), obj, True)
            return None

    return _find(key, obj)


def _camel_to_snake(s: str) -> str:
    if not isinstance(s):
        return s
    return string_utils.camel_case_to_snake(s)


class Serializer(ABC):
    def __init__(self):
        self._registered_types = {}

    def init(self):
        pass

    @abstractmethod
    def do_register(self, type_):
        pass

    def register_type(self, type_) -> "Serializer":
        type_ = _to_type(type_)
        self._registered_types[type_.__name__] = type_
        self.do_register(type_)
        return self

    @property
    def registered_types(self) -> list:
        return list(self._registered_types.values())

    def is_registered(self, obj) -> bool:
        assert obj is not None
        key = obj if isinstance(obj, str) else _to_type(obj).__name__
        return key in self._registered_types

    @abstractmethod
    def deserialize(self, i: str) -> object:
        pass

    @abstractmethod
    def serialize(self, i: object) -> str:
        pass


class JsonSerializer(Serializer):
    def __init__(self,):
        super().__init__()

    def do_register(self, type_):
        pass

    def deserialize(self, obj: str) -> object:
        as_dict = json.loads(obj)
        return self._deserialize(as_dict)

    def serialize(self, obj: object) -> str:
        assert self.is_registered(obj)
        as_dict = self._serialize(obj)
        return json.dumps(as_dict)

    def _serialize(self, obj: object):
        if isinstance(obj, list):
            return [self._serialize(i) for i in obj]
        elif isinstance(obj, dict):
            return {
                _sanitize_key(k): self._serialize(v)
                for k, v in obj.items()
                if not _is_empty(v)
            }
        elif isinstance(obj, enum.Enum):
            self.assert_is_registered(obj)
            return {
                _ENUM_TYPE_PROPERTY_KEY: _to_type(obj).__name__,
                _ENUM_VALUE_PROPERTY_KEY: obj.name,
            }
        elif hasattr(obj, "__class__") and hasattr(obj, "__dict__"):
            self.assert_is_registered(obj)
            o_dict = {
                _sanitize_key(k): self._serialize(v)
                for k, v in obj.__dict__.items()
                if not _is_empty(v)
            }
            o_dict[_TYPE_PROPERTY_KEY] = _to_type(obj).__name__
            return o_dict
        else:
            return obj

    def _deserialize(self, obj) -> object:
        if isinstance(obj, dict):

            # Dict of a class object
            if _TYPE_PROPERTY_KEY in obj:
                key = obj.pop(_TYPE_PROPERTY_KEY)
                self.assert_is_registered(key)

                class_type = self._registered_types[key]()
                for k, v in obj.items():
                    setattr(
                        class_type,
                        _find_correct_key(k, class_type),
                        self._deserialize(v),
                    )
                return class_type

            # Dict class of enum
            elif _ENUM_TYPE_PROPERTY_KEY in obj:
                assert (
                    _ENUM_VALUE_PROPERTY_KEY in obj
                ), f"{_ENUM_TYPE_PROPERTY_KEY} present but is missing {_ENUM_VALUE_PROPERTY_KEY}"

                key = obj.pop(_ENUM_TYPE_PROPERTY_KEY)
                self.assert_is_registered(key)
                enum_type = self._registered_types[key]
                enum_value = obj.pop(_ENUM_VALUE_PROPERTY_KEY)
                return enum_type[str(enum_value)]

            # Normal dict map
            else:
                return {
                    self._deserialize(k): self._deserialize(v) for k, v in obj.items()
                }

        elif isinstance(obj, list):
            return [self._deserialize(i) for i in obj]

        else:
            return obj

    def assert_is_registered(self, obj):
        assert self.is_registered(
            obj
        ), f"{obj if isinstance(obj, str) else _to_type(obj).__name__} not registered"


# class XstreamSerializer(Serializer):
#     """Simple port of the XStream XML serializer.
#
#     To maintain cross-compatibility between different languages, types are
#     serialized using their simple non-qualified class name. It is expected
#     that this contract is enforced throughout the Crossflow system.
#
#     All objects that are to be deserialized should be registered using the
#     alias method.
#     """
#
#     def __init__(self):
#         super().__init__()
#         self.aliases = {}
#
#     def serialize(self, obj):
#         # Extract name
#         if isinstance(obj, InternalException):
#             return self.__serialize_internal(obj)
#
#         name = self.aliases.get(type(obj), type(obj).__name__)
#         return xmltodict.unparse(
#             {name: obj.__dict__}, full_document=False, pretty=__debug__
#         )
#
#     def deserialize(self, xml):
#         parsed = xmltodict.parse(xml)
#         clazzname = list(parsed.keys())[0]
#         clazztype = self.aliases[clazzname]
#         instance = clazztype()
#
#         members = parsed[clazzname]
#         for key, raw in members.items():
#             rawType = type(raw)
#             value = raw
#
#             if rawType is int:
#                 value = int(raw)
#             elif rawType is float:
#                 value = float(raw)
#             elif rawType is bool:
#                 if raw.capitalize() == "True":
#                     value = True
#                 else:
#                     value = False
#             elif rawType is str:
#                 value = str(raw)
#             else:
#                 if str(rawType).startswith("<enum"):
#                     value = rawType.enum_from_name(raw)
#
#             setattr(instance, key, value)
#
#         return instance
#
#     def do_register(self, classType):
#         if not isinstance(classType, Type):
#             classType = type(classType)
#         self.aliases[classType.__name__] = classType
#
#     def alias(self, clazz):
#         if not isinstance(clazz, Type):
#             clazz = type(clazz)
#         self.aliases[clazz.__name__] = clazz
#
#     def __serialize_internal(self, ex):
#         exDict = {
#             "InternalException": {
#                 "exception": {
#                     "detailMessage": "!PYTHON!"
#                     + str(ex.exception)
#                     + "\n"
#                     + "\n".join(
#                         traceback.extract_stack(ex.exception.__traceback__).format()
#                     ),
#                     "stackTrace": {},
#                     "suppressedExceptions": {},
#                 }
#             }
#         }
#         return xmltodict.unparse(exDict, full_document=False, pretty=__debug__)
