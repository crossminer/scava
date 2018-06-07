{
    Object injectableValueId;
    String name;
    DeserializationConfig config;
    int index;
    AnnotatedParameter param;
    BasicBeanDescription beanDesc;
    JavaType t0 = config.getTypeFactory().constructType(param.getParameterType(), beanDesc.bindingsForBeanType());
    BeanProperty.Std property = new BeanProperty.Std(name, t0, beanDesc.getClassAnnotations(), param);
    JavaType type = resolveType(config, beanDesc, t0, param, property);
    JsonDeserializer<Object> deser = findDeserializerFromAnnotation(config, param, property);
    type = modifyTypeByAnnotation(config, param, type, name);

    TypeDeserializer typeDeser = (TypeDeserializer) type.getTypeHandler();
    if (typeDeser == null) {
        typeDeser = findTypeDeserializer(config, type, property);
    }
    CreatorProperty prop = new CreatorProperty(name, type, typeDeser,
    beanDesc.getClassAnnotations(), param, index, injectableValueId);
    if (deser != null) {
        prop = prop.withValueDeserializer(deser);
        // Do something with prop
    }
}