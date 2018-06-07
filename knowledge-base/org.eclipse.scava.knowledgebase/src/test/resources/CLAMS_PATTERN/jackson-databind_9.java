{
    final AnnotatedMember _accessor;
    SerializationConfig config;
    _accessor.fixAccess(
        config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
}