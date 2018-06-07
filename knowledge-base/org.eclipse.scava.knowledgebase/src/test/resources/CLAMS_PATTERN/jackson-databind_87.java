{
    JavaType t;
    JavaType _nonTrivialBaseType;
    PropertySerializerMap map;
    SerializerProvider provider;
    Class<?> rawType;
    if (_nonTrivialBaseType != null) {
        t = provider.constructSpecializedType(_nonTrivialBaseType,
        rawType);
    } else {
        t = provider.constructType(rawType);
    }
    PropertySerializerMap.SerializerAndMapResult result = map.findAndAddPrimarySerializer(t, provider, this);
    // Do something with result
}