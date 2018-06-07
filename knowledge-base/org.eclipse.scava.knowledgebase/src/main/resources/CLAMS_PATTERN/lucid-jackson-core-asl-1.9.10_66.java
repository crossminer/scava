{
    SerializationConfig config;
    Collection<NamedType> subtypes;
    BeanProperty property;
    JavaType baseType;
    BasicBeanDescription bean = config.introspectClassAnnotations(baseType.getRawClass());
    AnnotatedClass ac = bean.getClassInfo();
    AnnotationIntrospector ai = config.getAnnotationIntrospector();
    TypeResolverBuilder<?> b = ai.findTypeResolver(config, ac, baseType);
    if (b == null) {
        b = config.getDefaultTyper(baseType);
    } else {
        subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(ac, config, ai);
    }
    return (b == null) ? null : b.buildTypeSerializer(config, baseType, subtypes, property);
}