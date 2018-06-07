{
    BeanProperty.Std property;
    SerializationConfig config;
    BeanPropertyWriter pbw;
    TypeSerializer contentTypeSer;
    AnnotatedMember accessor;
    TypeBindings typeContext;
    if (config.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
        accessor.fixAccess();
    }
    JavaType type = accessor.getType(typeContext);
    JsonSerializer<Object> annotatedSerializer = findSerializerFromAnnotation(config, accessor, property);
    if (ClassUtil.isCollectionMapOrArray(type.getRawClass())) {
        contentTypeSer = findPropertyContentTypeSerializer(type, config, accessor, property);
        // Do something with contentTypeSer
    }

    TypeSerializer typeSer = findPropertyTypeSerializer(type, config, accessor, property);
    AnnotationIntrospector intr = config.getAnnotationIntrospector();
    pbw.setViews(intr.findSerializationViews(accessor));
    // Do something with annotatedSerializer

    // Do something with typeSer
}