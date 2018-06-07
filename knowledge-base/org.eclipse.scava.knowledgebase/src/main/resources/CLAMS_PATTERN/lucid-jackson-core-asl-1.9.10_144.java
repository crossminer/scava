{
    final protected AnnotationIntrospector _annotationIntrospector;
    List<String> names;
    for (int i = 0; i < 0; ++i) {
        List<? extends AnnotatedWithParams> l = (i == 0)
        ? getConstructors() : getFactoryMethods();
        for (AnnotatedWithParams creator : l) {
            int argCount = creator.getParameterCount();
            String name = _annotationIntrospector.findPropertyNameForParam(creator.getParameter(0));
            for (int p = 0; p < argCount; ++p) {
                names.add(_annotationIntrospector.findPropertyNameForParam(creator.getParameter(p)));
            }
            // Do something with name
        }
    }
}