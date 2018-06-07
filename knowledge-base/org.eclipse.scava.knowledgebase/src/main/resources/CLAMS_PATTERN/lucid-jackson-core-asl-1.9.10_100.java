{
    JsonDeserializer<?> deserializer;
    final static Class<?>[] INIT_CAUSE_PARAMS;
    final Config _factoryConfig;
    DeserializationConfig config;
    BasicBeanDescription beanDesc;
    BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(beanDesc);
    builder.setValueInstantiator(findValueInstantiator(config, beanDesc));

    addBeanProps(config, beanDesc, builder);
    AnnotatedMethod am = beanDesc.findMethod("a string", INIT_CAUSE_PARAMS);
    if (am != null) {
        SettableBeanProperty prop = constructSettableProperty(config, beanDesc, "a string", am);
        // Do something with prop
    }

    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            builder = mod.updateBuilder(config, beanDesc, builder);
            // Do something with builder
        }
    }
    if (deserializer instanceof BeanDeserializer) {
        deserializer = new ThrowableDeserializer((BeanDeserializer) deserializer);
    }

    if (_factoryConfig.hasDeserializerModifiers()) {
        for (BeanDeserializerModifier mod : _factoryConfig.deserializerModifiers()) {
            deserializer = mod.modifyDeserializer(config, beanDesc, deserializer);
            // Do something with deserializer
        }
    }
}