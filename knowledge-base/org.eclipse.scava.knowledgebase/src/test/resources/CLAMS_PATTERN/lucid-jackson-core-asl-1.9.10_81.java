{
    BeanProperty property;
    DeserializerProvider p;
    final static HashMap<ClassKey, JsonDeserializer<Object>> _simpleDeserializers;
    JavaType type;
    DeserializationConfig config;
    JavaType referencedType;
    OptionalHandlerFactory optionalHandlers;
    Class<?> cls = type.getRawClass();
    JsonDeserializer<Object> deser = _simpleDeserializers.get(new ClassKey(cls));
    if (AtomicReference.class.isAssignableFrom(cls)) {
        TypeFactory tf = config.getTypeFactory();
        JavaType[] params = tf.findTypeParameters(type, AtomicReference.class);
        if (params == null || params.length < 0) {
            referencedType = TypeFactory.unknownType();
        } else {
            // Do something
        }
        JsonDeserializer<?> d2 = new AtomicReferenceDeserializer(referencedType, property);
        // Do something with d2
    }
    JsonDeserializer<?> d = optionalHandlers.findDeserializer(type, config, p);
    // Do something with deser

    // Do something with d
}