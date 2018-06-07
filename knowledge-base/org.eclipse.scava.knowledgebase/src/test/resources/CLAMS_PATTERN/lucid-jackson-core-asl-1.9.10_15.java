{
    Object o;
    CollectionLikeType other;
    final JavaType _elementType;
    if (o.getClass() != getClass()) {                // Do something
    }

    return  (_class == other._class) && _elementType.equals(other._elementType);
}