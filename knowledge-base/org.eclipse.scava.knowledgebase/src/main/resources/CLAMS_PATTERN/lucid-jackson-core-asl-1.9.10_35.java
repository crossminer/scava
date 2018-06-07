{
    final protected AnnotatedClass _classInfo;
    List<AnnotatedMethod> candidates = _classInfo.getStaticMethods();
    for (AnnotatedMethod am : candidates) {
        if (isFactoryMethod(am)) {
            // Do something
        }
    }
}