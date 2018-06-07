{
    final Class<?> _class;
    final MixInResolver _mixInResolver;
    final AnnotationIntrospector _intr;
    final Class<?> _primaryMixin;
    final JavaType _type;
    final MapperConfig<?> _config;
    final TypeBindings _bindings;
    List<JavaType> superTypes = ClassUtil.findSuperTypes(_type, null, boolean);
    return new AnnotatedClass(_type, _class, superTypes, _primaryMixin,
    resolveClassAnnotations(superTypes),
    _bindings, _intr, _mixInResolver, _config.getTypeFactory());

}