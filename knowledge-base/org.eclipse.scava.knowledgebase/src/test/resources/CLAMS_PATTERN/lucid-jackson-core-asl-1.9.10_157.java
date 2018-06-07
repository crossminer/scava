{
    BeanProperty property;
    DeserializationConfig config;
    JavaType baseType;
    AnnotatedMember annotated;
    AnnotationIntrospector ai = config.getAnnotationIntrospector();
    TypeResolverBuilder<?> b = ai.findPropertyTypeResolver(config, annotated, baseType);
    if (b == null) {
        return findTypeDeserializer(config, baseType, property);
    }
    Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(annotated, config, ai);
    return b.buildTypeDeserializer(config, baseType, subtypes, property);
}