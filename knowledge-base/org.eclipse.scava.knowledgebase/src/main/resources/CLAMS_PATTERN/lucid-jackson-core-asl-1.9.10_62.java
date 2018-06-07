{
    DeserializationConfig config;
    final Config _factoryConfig;
    KeyDeserializer kdes;
    JavaType type;
    BeanProperty property;
    if (_factoryConfig.hasKeyDeserializers()) {
        BasicBeanDescription beanDesc = config.introspectClassAnnotations(type.getRawClass());
        // Do something with beanDesc
    }
    Class<?> raw = type.getRawClass();
    if (raw == String.class || raw == Object.class) {
        return StdKeyDeserializers.constructStringKeyDeserializer(config, type);
    }
    if (type.isEnumType()) {
        return _createEnumKeyDeserializer(config, type, property);
    }
    kdes = StdKeyDeserializers.findStringBasedKeyDeserializer(config, type);
    // Do something with kdes
}