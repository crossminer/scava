{
    JavaType type;
    MixInResolver r;
    SerializationConfig cfg;
    final transient SimpleLookupCache<JavaType,BasicBeanDescription> _cachedFCA;
    BasicBeanDescription desc = _findStdTypeDesc(type);
    if (desc == null) {
        desc = _findStdJdkCollectionDesc(cfg, type);
        if (desc == null) {
            desc = BasicBeanDescription.forSerialization(collectProperties(cfg,
            type, r, boolean, "a string"));
        }
        _cachedFCA.putIfAbsent(type, desc);
    }
}