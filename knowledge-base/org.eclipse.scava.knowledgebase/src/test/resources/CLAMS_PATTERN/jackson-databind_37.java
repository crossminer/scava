{
    Class<?>... expArgTypes;
    final protected AnnotatedClass _classInfo;
    for (AnnotatedMethod am : _classInfo.getFactoryMethods()) {
        if (isFactoryMethod(am) && am.getParameterCount() == 0) {
            Class<?> actualArgType = am.getRawParameterType(0);
            for (Class<?> expArgType : expArgTypes) {
                if (actualArgType.isAssignableFrom(expArgType)) {
                    return am.getAnnotated();
                }
            }
        }
    }
}