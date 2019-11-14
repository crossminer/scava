import enum
import json
from abc import ABC, abstractmethod

import string_utils

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
                    setattr(class_type, k, self._deserialize(v))
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
        assert self.is_registered(obj), f"{obj if isinstance(obj, str) else _to_type(obj).__name__} not registered"
