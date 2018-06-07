{
    JavaType type;
    MixInResolver r;
    DeserializationConfig cfg;
    final transient SimpleLookupCache<JavaType,BasicBeanDescription> _cachedFCA;
    BasicBeanDescription desc = BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder(cfg,
    type, r, boolean));
    _cachedFCA.putIfAbsent(type, desc);
}