{
    MapperConfig<?> config;
    MixInResolver r;
    JavaType type;
    BasicBeanDescription desc = _findStdTypeDesc(type);
    if (desc == null) {
        desc = BasicBeanDescription.forOtherUse(config, type,
        _resolveAnnotatedWithoutSuperTypes(config, type, r));
        // Do something with desc
    }
}