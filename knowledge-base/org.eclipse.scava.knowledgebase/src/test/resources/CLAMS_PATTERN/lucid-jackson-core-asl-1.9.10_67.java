{
    JavaType mapType;
    Constructor<Map<Object,Object>> defCtor;
    StdValueInstantiator inst = new StdValueInstantiator(null, mapType);
    if (defCtor != null) {
        AnnotatedConstructor aCtor = new AnnotatedConstructor(defCtor,
        null, null);
        inst.configureFromObjectSettings(aCtor, null, null, null, null);
    }
}