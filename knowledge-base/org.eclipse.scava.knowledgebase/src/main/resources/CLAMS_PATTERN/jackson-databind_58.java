{
    Class<?> type;
    Boolean status;
    DeserializationConfig config;
    if ((type == String.class) || type.isPrimitive()) {
        // Do something
    } else {
        status = config.getConfigOverride(type).getIsIgnoredType();
        if (status == null) {
            BeanDescription desc = config.introspectClassAnnotations(type);
            status = config.getAnnotationIntrospector().isIgnorableType(desc.getClassInfo());
            // Do something with status
        }
    }
}