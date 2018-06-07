{
    MapperConfig<?> config;
    AnnotatedMember am;
    JavaType baseType;
    if (baseType.isContainerType()) {                // Do something
    }
    return _findTypeResolver(config, am, baseType);
}