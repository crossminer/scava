{
    AnnotatedClass classInfo;
    DeserializationContext ctxt;
    Collection<NamedType> subtypes;
    JavaType baseType;
    final DeserializationConfig config = ctxt.getConfig();
    TypeResolverBuilder<?> b = _findTypeResolver(config, classInfo, baseType);

    if (b == null) {
        b = config.getDefaultTyper(baseType);
    } else {
        subtypes = config.getSubtypeResolver().collectAndResolveSubtypesByTypeId(config, classInfo);
    }
    if ((b.getDefaultImpl() == null) && baseType.isAbstract()) {
        JavaType defaultType = config.mapAbstractType(baseType);
        if ((defaultType != null) && !defaultType.hasRawClass(baseType.getRawClass())) {
            b = b.defaultImpl(defaultType.getRawClass());
        }
    }
    return b.buildTypeDeserializer(config, baseType, subtypes);
}