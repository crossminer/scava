{
    SerializerProvider ctxt;
    Collection<NamedType> subtypes;
    JavaType baseType;
    AnnotatedClass classInfo;
    final SerializationConfig config = ctxt.getConfig();
    TypeResolverBuilder<?> b = _findTypeResolver(config, classInfo, baseType);
    if (b == null) {
        b = config.getDefaultTyper(baseType);
    } else {
        subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByClass(config, classInfo);
    }
    return b.buildTypeSerializer(config, baseType, subtypes);
}