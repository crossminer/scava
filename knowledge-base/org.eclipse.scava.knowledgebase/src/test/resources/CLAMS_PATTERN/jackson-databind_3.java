{
    SerializationConfig config;
    BeanDescription beanDesc;
    return config.getAnnotationIntrospector().findFilterId((Annotated)beanDesc.getClassInfo());
}