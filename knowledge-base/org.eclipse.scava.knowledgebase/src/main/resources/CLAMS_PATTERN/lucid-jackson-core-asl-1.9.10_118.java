{
    SerializationConfig config;
    JsonSerializer<Object> keySerializer;
    JsonSerializer<Object> elementValueSerializer;
    boolean staticTyping;
    MapType type;
    BasicBeanDescription beanDesc;
    BeanProperty property;
    TypeSerializer elementTypeSerializer;
    for (Serializers serializers : customSerializers()) {
        // Do something
    }
    if (EnumMap.class.isAssignableFrom(type.getRawClass())) {
        return buildEnumMapSerializer(config, type, beanDesc, property, staticTyping,
        elementTypeSerializer, elementValueSerializer);
    }
    return MapSerializer.construct(config.getAnnotationIntrospector().findPropertiesToIgnore(beanDesc.getClassInfo()),
    type, staticTyping, elementTypeSerializer, property,
    keySerializer, elementValueSerializer);
}