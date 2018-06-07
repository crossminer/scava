{
    SerializationConfig config;
    Annotated a;
    T type;
    AnnotationIntrospector intr = config.getAnnotationIntrospector();
    if (type.isContainerType()) {
        Class<?> keyClass = intr.findSerializationKeyType(a, type.getKeyType());
        Class<?> cc = intr.findSerializationContentType(a, type.getContentType());
        // Do something with keyClass
        // Do something with cc
    }
}