{
    Class<? extends ValueInstantiator> instClass;
    final Config _factoryConfig;
    ValueInstantiator instantiator;
    Class<?> cls;
    DeserializationConfig config;
    BasicBeanDescription beanDesc;
    AnnotatedClass ac = beanDesc.getClassInfo();
    Object instDef = config.getAnnotationIntrospector().findValueInstantiator(ac);
    if (instDef != null) {
        if (instDef instanceof ValueInstantiator) {
            // Do something
        } else {
            if (!ValueInstantiator.class.isAssignableFrom(cls)) {
                // Do something
            }
            instantiator = config.valueInstantiatorInstance(ac, (Class<? extends ValueInstantiator>)instClass);
        }
    } else {
        instantiator = constructDefaultValueInstantiator(config, beanDesc);
    }

    if (_factoryConfig.hasValueInstantiators()) {
        for (ValueInstantiators insts : _factoryConfig.valueInstantiators()) {
            instantiator = insts.findValueInstantiator(config, beanDesc, instantiator);
            if (instantiator == null) {
                throw new JsonMappingException("a string"
                                               +insts.getClass().getName()+"a string");
            }
        }
    }
}