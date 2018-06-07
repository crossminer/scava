{
    Class<?> contentClass;
    if (contentClass == _elementType.getRawClass()) {
        // Do something
    }
    return new CollectionType(_class, _elementType.narrowBy(contentClass),
    _valueHandler, _typeHandler);
}