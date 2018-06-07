{
    final JavaType _componentType;
    Object h;
    final Object _emptyArray;
    if (h == _componentType.<Object>getTypeHandler()) {
        // Do something
    }
    return new ArrayType(_componentType.withTypeHandler(h), _bindings, _emptyArray,
    _valueHandler, _typeHandler, _asStatic);
}