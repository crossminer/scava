import json
from abc import ABC, abstractmethod

_TYPE_PROPERTY_KEY = "_type_"


def _to_type(type_):
    return type_ if isinstance(type_, type) else type(type_)


class Serializer(ABC):
    def init(self):
        pass

    @abstractmethod
    def register_type(self, type_) -> "Serializer":
        pass

    @property
    @abstractmethod
    def registered_types(self) -> list:
        pass

    @abstractmethod
    def is_registered(self, type_) -> bool:
        pass

    @abstractmethod
    def deserialize(self, i: str) -> object:
        pass

    @abstractmethod
    def serialize(self, i: object) -> str:
        pass


class JsonSerializer(Serializer):
    def __init__(self):
        self._registered_types = {}

    @property
    def registered_types(self) -> list:
        return list(self._registered_types.values())

    def register_type(self, type_: type) -> "JsonSerializer":
        type_ = _to_type(type_)
        self._registered_types[type_.__name__] = type_
        return self

    def is_registered(self, type_) -> bool:
        type_ = _to_type(type_)
        return type_.__name__ in self._registered_types

    def deserialize(self, i: str) -> object:
        as_dict = json.loads(i)
        print(f"deserialized as_dict:\n{as_dict}\n")
        return self._deserialize(as_dict)

    def serialize(self, i: object) -> str:
        as_dict = self._serialize(i)
        print(f"serialize as_dict:\n{as_dict}\n")
        return json.dumps(as_dict)

    def _serialize(self, o: object):
        if isinstance(o, list):
            return [self._serialize(i) for i in o]
        elif isinstance(o, dict):
            return {self._serialize(k): self._serialize(v) for k, v in o.items()}
        elif hasattr(o, "__class__") and hasattr(o, "__dict__"):
            o_dict = {
                self._serialize(k): self._serialize(v) for k, v in o.__dict__.items()
            }
            o_dict[_TYPE_PROPERTY_KEY] = _to_type(o).__name__
            return o_dict
        else:
            return o

    def _deserialize(self, o) -> object:
        if isinstance(o, dict):

            # Dict of a class object
            if _TYPE_PROPERTY_KEY in o:
                inst = self._registered_types[o.pop(_TYPE_PROPERTY_KEY)]()
                assert _TYPE_PROPERTY_KEY not in o
                for k, v in o.items():
                    setattr(inst, k, self._deserialize(v))
                return inst

            # Normal dict map
            else:
                return {
                    self._deserialize(k): self._deserialize(v) for k, v in o.items()
                }

        elif isinstance(o, list):
            return [self._deserialize(i) for i in o]

        else:
            return o


class DummyObject:
    def __init__(self):
        self.stringProp = "default"
        self.intProp = 123
        self.floatProp = 321.0
        self.boolProp = False
        self.listProp = []

    def test(self):
        print("sfsf")


if __name__ == "__main__":
    j = JsonSerializer()
    j.register_type(DummyObject)
    actual = DummyObject()
    actual.stringProp = "parent"
    actual.listProp.append(DummyObject())

    serialized = j.serialize(actual)
    print(f"serialized json:\n{serialized}\n")

    de = j.deserialize(serialized)
    print(de.__dict__)
    print(de.listProp[0].__dict__)
