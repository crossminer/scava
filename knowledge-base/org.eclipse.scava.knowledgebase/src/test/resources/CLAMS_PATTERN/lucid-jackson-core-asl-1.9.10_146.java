{
    JsonDeserializer<?> deserializer;
    final Config _factoryConfig;
    DeserializationConfig config;
    BasicBeanDescription beanDesc;
    JavaType type;
    ValueInstantiator valueInstantiator = findValueInstantiator(config, beanDesc);
    if (type.isAbstract()) {
        if (!valueInstantiator.canInstantiate()) {
            // Do something
        }
    }
    BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(beanDesc);
    addBeanProps(config, beanDesc, builder);
    addReferenceProperties(config, beanDesc, builder);
    addInjectables(config, beanDesc, builder);

    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            builder = mod.updateBuilder(config, beanDesc, builder);
            // Do something with builder
        }
    }
    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            deserializer = mod.modifyDeserializer(config, beanDesc, deserializer);
            // Do something with deserializer
        }
    }
}