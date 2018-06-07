{
    JavaType type;
    Class<Enum<?>> enumClass;
    SerializationConfig config;
    BasicBeanDescription beanDesc;
    BeanProperty property;
    OptionalHandlerFactory optionalHandlers;
    Class<?> raw = type.getRawClass();
    if (JsonSerializable.class.isAssignableFrom(raw)) {
        if (JsonSerializableWithType.class.isAssignableFrom(raw)) {
            // Do something
        }
    }
    AnnotatedMethod valueMethod = beanDesc.findJsonValueMethod();
    if (valueMethod != null) {
        Method m = valueMethod.getAnnotated();
        if (config.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            ClassUtil.checkAndFixAccess(m);
        }
        JsonSerializer<Object> ser = findSerializerFromAnnotation(config, valueMethod, property);
        return new JsonValueSerializer(m, ser, property);
    }

    if (InetAddress.class.isAssignableFrom(raw)) {
        // Do something
    }
    if (TimeZone.class.isAssignableFrom(raw)) {
        // Do something
    }
    if (java.nio.charset.Charset.class.isAssignableFrom(raw)) {
        // Do something
    }

    JsonSerializer<?> ser = optionalHandlers.findSerializer(config, type);
    if (Number.class.isAssignableFrom(raw)) {
        // Do something
    }
    if (Enum.class.isAssignableFrom(raw)) {
        return EnumSerializer.construct(enumClass, config, beanDesc);
    }
    if (Calendar.class.isAssignableFrom(raw)) {
        // Do something
    }
    if (java.util.Date.class.isAssignableFrom(raw)) {
        // Do something
    }
    // Do something with ser
}