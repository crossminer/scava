{
    final Class<?> _class;
    final MixInResolver _mixInResolver;
    final Class<?> _primaryMixin;
    List<JavaType> superTypes;
    AnnotationCollector resolvedCA = AnnotationCollector.emptyCollector();
    if (_primaryMixin != null) {
        resolvedCA = _addClassMixIns(resolvedCA, _class, _primaryMixin);
    }
    resolvedCA = _addAnnotationsIfNotPresent(resolvedCA,
    ClassUtil.findClassAnnotations(_class));

    for (JavaType type : superTypes) {
        if (_mixInResolver != null) {
            Class<?> cls = type.getRawClass();
            resolvedCA = _addClassMixIns(resolvedCA, cls,
            _mixInResolver.findMixInClassFor(cls));
        }
        resolvedCA = _addAnnotationsIfNotPresent(resolvedCA,
                     ClassUtil.findClassAnnotations(type.getRawClass()));
    }
    if (_mixInResolver != null) {
        resolvedCA = _addClassMixIns(resolvedCA, Object.class,
                                     _mixInResolver.findMixInClassFor(Object.class));
    }
    return resolvedCA.asAnnotations();
}