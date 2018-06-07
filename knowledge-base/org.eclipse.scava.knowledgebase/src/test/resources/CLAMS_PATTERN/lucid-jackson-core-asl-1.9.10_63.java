{
    final JavaType _componentType;
    final Object _emptyArray;
    Object h;
    if (h == _componentType.getTypeHandler()) {
        // Do something
    }
    return new ArrayType(_componentType.withTypeHandler(h), _emptyArray,
    _valueHandler, _typeHandler);
}