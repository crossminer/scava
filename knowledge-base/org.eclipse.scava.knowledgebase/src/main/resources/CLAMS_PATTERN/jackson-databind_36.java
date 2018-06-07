{
    DeserializationContext ctxt;
    Object injectableValueId;
    BeanDescription beanDesc;
    PropertyName name;
    PropertyMetadata metadata;
    AnnotatedParameter param;
    int index;
    final AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
    {
        if (intr == null) {
            // Do something
        } else {
            Boolean b = intr.hasRequiredMarker(param);
            String desc = intr.findPropertyDescription(param);
            Integer idx = intr.findPropertyIndex(param);
            String def = intr.findPropertyDefaultValue(param);
            // Do something with b
            // Do something with desc
            // Do something with idx
            // Do something with def
        }
    }
    JavaType type = resolveMemberAndTypeAnnotations(ctxt, param, param.getType());
    BeanProperty.Std property = new BeanProperty.Std(name, type,
    intr.findWrapperName(param), param, metadata);
    TypeDeserializer typeDeser = (TypeDeserializer) type.getTypeHandler();
    if (typeDeser == null) {
        typeDeser = ctxt.findTypeDeserializer(type);
    }
    SettableBeanProperty prop = new CreatorProperty(name, type, property.getWrapperName(),
    typeDeser, beanDesc.getClassAnnotations(), param, index, injectableValueId,
    metadata);
    JsonDeserializer<?> deser = findDeserializerFromAnnotation(ctxt, param);
    if (deser == null) {
        deser = type.getValueHandler();
    }
    if (deser != null) {
        deser = ctxt.handlePrimaryContextualization(deser, prop, type);
        prop = prop.withValueDeserializer(deser);
        // Do something with prop
    }
}