{
    DeserializationConfig config;
    BeanProperty property;
    DeserializerProvider p;
    ArrayType type;
    JsonDeserializer<?> deser;
    JavaType elemType = type.getContentType();

    JsonDeserializer<Object> contentDeser = elemType.getValueHandler();
    if (contentDeser == null) {
        if (deser != null) {
            JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, null, null);
        }
        if (elemType.isPrimitive()) {
            // Do something
        }
    }
    TypeDeserializer elemTypeDeser = elemType.getTypeHandler();
    if (elemTypeDeser == null) {
        elemTypeDeser = findTypeDeserializer(config, elemType, property);
    }
    JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, config, p, property, elemTypeDeser, contentDeser);
    return new ObjectArrayDeserializer(type, contentDeser, elemTypeDeser);
    // Do something with custom

}