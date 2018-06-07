{
    final protected AnnotationIntrospector _annotationIntrospector;
    final protected MapperConfig<?> _config;
    final protected AnnotatedClass _classInfo;
    return _createConverter(_annotationIntrospector
    .findDeserializationConverter(_config, _classInfo));
}