{
    Class<?> contentClass;
    final JavaType _componentType;
    if (contentClass == _componentType.getRawClass()) {
        // Do something
    }
    return construct(_componentType.narrowBy(contentClass),
    _valueHandler, _typeHandler);
}