{
    AnnotatedMethod am;
    if (!isVisible(am)) {
        // Do something
    }
    String name = findJaxbPropertyName(am, am.getRawType(),
    BeanUtil.okNameForGetter(am));
    // Do something with name
}