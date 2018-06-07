{
    DeserializationContext ctxt;
    final BeanDescription beanDesc;
    final DeserializerFactoryConfig _factoryConfig;
    ArrayType type;
    final DeserializationConfig config = ctxt.getConfig();
    JavaType elemType = type.getContentType();

    JsonDeserializer<Object> contentDeser = elemType.getValueHandler();
    TypeDeserializer elemTypeDeser = elemType.getTypeHandler();
    if (elemTypeDeser == null) {
        elemTypeDeser = ctxt.findTypeDeserializer(elemType);
    }
    JsonDeserializer<?>  deser = _findCustomArrayDeserializer(type,
    config, beanDesc, elemTypeDeser, contentDeser);
    if (deser == null) {
        if (contentDeser == null) {
            Class<?> raw = elemType.getRawClass();
            if (elemType.isPrimitive()) {
                // Do something
            }
            // Do something with raw
        }
    }
    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            // Do something
        }
    }
}