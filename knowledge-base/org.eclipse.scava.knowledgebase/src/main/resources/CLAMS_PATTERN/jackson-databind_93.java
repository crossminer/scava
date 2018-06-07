{
    SerializerProvider ctxt;
    JsonSerializer<?> delegate;
    final SerializerFactoryConfig _factoryConfig;
    JavaType keyType;
    JsonSerializer<?> ser;
    BeanDescription beanDesc = ctxt.introspectClassAnnotations(keyType);
    final SerializationConfig config = ctxt.getConfig();
    if (_factoryConfig.hasKeySerializers()) {
        for (Serializers serializers : _factoryConfig.keySerializers()) {
            // Do something
        }
    }
    if (ser == null) {
        ser = StdKeySerializers.getStdKeySerializer(config, keyType.getRawClass(), boolean);
        if (ser == null) {
            beanDesc = ctxt.introspect(keyType);
            AnnotatedMember am = beanDesc.findJsonValueAccessor();
            if (am != null) {
                final Class<?> rawType = am.getRawType();
                if (config.canOverrideAccessModifiers()) {
                    ClassUtil.checkAndFixAccess(am.getMember(),
                                                config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                }
                ser = new JsonValueSerializer(keyType, am.getType(), boolean, null, delegate, am);
                // Do something with rawType
            } else {
                if (ser == null) {
                    ser = StdKeySerializers.getFallbackKeySerializer(config, keyType.getRawClass());
                }
            }
        }
    }

    if (_factoryConfig.hasSerializerModifiers()) {
        for (BeanSerializerModifier mod : _factoryConfig.serializerModifiers()) {
            ser = mod.modifyKeySerializer(config, keyType, beanDesc, ser);
            // Do something with ser
        }
    }
}