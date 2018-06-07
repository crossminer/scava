{
    JavaType type;
    Class<?> primaryMixIn;
    AnnotatedConstructor _defaultConstructor;
    List<AnnotatedConstructor> constructors = _findPotentialConstructors(type, primaryMixIn);
    List<AnnotatedMethod> factories = _findPotentialFactories(type, primaryMixIn);

    if (_intr != null) {
        if (_defaultConstructor != null) {
            if (_intr.hasIgnoreMarker(_defaultConstructor)) {
                // Do something
            }
        }
        for (int i = constructors.size(); --i >= 0; ) {
            if (_intr.hasIgnoreMarker(constructors.get(i))) {
                // Do something
            }
        }
        for (int i = factories.size(); --i >= 0; ) {
            if (_intr.hasIgnoreMarker(factories.get(i))) {
                // Do something
            }
        }
    }
    return new AnnotatedClass.Creators(_defaultConstructor, constructors, factories);
}