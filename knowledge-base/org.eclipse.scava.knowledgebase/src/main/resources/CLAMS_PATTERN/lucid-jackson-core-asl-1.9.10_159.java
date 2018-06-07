{
    Class<?>... expArgTypes;
    final protected AnnotatedClass _classInfo;
    for (AnnotatedMethod am : _classInfo.getStaticMethods()) {
        if (isFactoryMethod(am)) {
            Class<?> actualArgType = am.getParameterClass(0);
            for (Class<?> expArgType : expArgTypes) {
                if (actualArgType.isAssignableFrom(expArgType)) {
                    return am.getAnnotated();
                }
            }
        }
    }
}