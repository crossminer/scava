{
    final Config _factoryConfig;
    DeserializationConfig config;
    BasicBeanDescription beanDesc;
    final JavaType abstractType = beanDesc.getType();

    for (AbstractTypeResolver r : _factoryConfig.abstractTypeResolvers()) {
        JavaType concrete = r.resolveAbstractType(config, abstractType);
        // Do something with concrete
    }
}