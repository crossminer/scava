{
    MapperConfig<?> config;
    final Class<?> _class;
    final AnnotationIntrospector _intr;
    final Class<?> _primaryMixin;
    JavaType type;
    final MapperConfig<?> _config;
    final TypeBindings _bindings;
    _class = type.getRawClass();
    _bindings = type.getBindings();
    _intr = config.isAnnotationProcessingEnabled()
    ? config.getAnnotationIntrospector() : null;
    _primaryMixin = _config.findMixInClassFor(_class);
    // Do something with _bindings

    // Do something with _intr

    // Do something with _primaryMixin

}