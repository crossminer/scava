{
    SerializationConfig config;
    Annotated a;
    Class<?> cls;
    Object serDef = config.getAnnotationIntrospector().findSerializer(a);
    if (!JsonSerializer.class.isAssignableFrom(cls)) {
        // Do something
    }
    JsonSerializer<Object> ser = config.serializerInstance(a, (Class<? extends JsonSerializer<?>>) cls);
    // Do something with serDef

    // Do something with ser
}