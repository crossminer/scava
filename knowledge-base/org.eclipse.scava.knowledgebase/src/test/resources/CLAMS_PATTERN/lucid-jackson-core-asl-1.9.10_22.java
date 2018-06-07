{
    JavaType type;
    MixInResolver r;
    DeserializationConfig cfg;
    BasicBeanDescription desc = _findCachedDesc(type);
    if (desc == null) {
        desc = BasicBeanDescription.forDeserialization(collectProperties(cfg, type, r, boolean));
        // Do something with desc
    }
}