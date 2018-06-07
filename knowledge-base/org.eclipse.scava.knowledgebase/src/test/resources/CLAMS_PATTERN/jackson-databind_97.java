{
    MapperConfig<?> config;
    final AnnotationIntrospector _primary;
    Annotated am;
    Object r = _primary.findSerializer(config, am);
    if (_isExplicitClassOrOb(r, JsonSerializer.None.class)) {
        // Do something
    }
    return _explicitClassOrOb(_secondary.findSerializer(config, am),
    JsonSerializer.None.class);
}