{
    final protected AnnotationIntrospector _annotationIntrospector;
    AnnotatedMethod am;
    Class<?> rt = am.getRawType();
    if (!getBeanClass().isAssignableFrom(rt)) {
        // Do something
    }

    if (_annotationIntrospector.hasCreatorAnnotation(am)) {
        // Do something
    }
    if ("a string".equals(am.getName())) {
        // Do something
    }
}