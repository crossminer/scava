{
    AnnotatedMethod factory;
    DeserializationConfig config;
    Class<?> enumClass;
    Class<?> raw = factory.getParameterClass(0);
    if (config.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
        ClassUtil.checkAndFixAccess(factory.getMember());
    }
    return new FactoryBasedDeserializer(enumClass, factory, raw);
}