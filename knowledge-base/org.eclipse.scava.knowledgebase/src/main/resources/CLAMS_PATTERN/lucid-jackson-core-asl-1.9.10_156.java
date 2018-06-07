{
    BeanProperty property;
    DeserializationConfig config;
    JavaType baseType;
    Collection<NamedType> subtypes;
    Class<?> cls = baseType.getRawClass();
    BasicBeanDescription bean = config.introspectClassAnnotations(cls);
    AnnotatedClass ac = bean.getClassInfo();
    AnnotationIntrospector ai = config.getAnnotationIntrospector();
    TypeResolverBuilder<?> b = ai.findTypeResolver(config, ac, baseType);

    if (b == null) {
        b = config.getDefaultTyper(baseType);
    } else {
        subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(ac, config, ai);
    }
    if ((b.getDefaultImpl() == null) && baseType.isAbstract()) {
        JavaType defaultType = mapAbstractType(config, baseType);
        if (defaultType != null && defaultType.getRawClass() != baseType.getRawClass()) {
            b = b.defaultImpl(defaultType.getRawClass());
        }
    }
    return b.buildTypeDeserializer(config, baseType, subtypes, property);
}