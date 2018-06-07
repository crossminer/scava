{
    DeserializationConfig config;
    JavaType type;
    if (type.isContainerType() || type.isPrimitive() || type.isEnumType() || type.isThrowable()) {
        // Do something
    }
    Class<?> cls = type.getRawClass();
    if (!Modifier.isPublic(cls.getModifiers())) {
        if (isEnabled(Feature.FAIL_ON_NON_PUBLIC_TYPES)) {
            // Do something
        }
    }

    return config.constructType(materializeClass(config, cls));
}