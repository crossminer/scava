{
    SerializerProvider provider;
    final protected BeanPropertyWriter[] _props;
    int filteredCount;
    final protected BeanPropertyWriter[] _filteredProps;
    BeanPropertyWriter prop;
    BeanPropertyWriter w2;
    for (int i = 0, len = _props.length; i < len; ++i) {
        if (prop.hasSerializer()) {
            continue;
        }
        JavaType type = prop.getSerializationType();
        if (type == null) {
            type = provider.constructType(prop.getGenericPropertyType());
            if (!type.isFinal()) {
                if (type.isContainerType() || type.containedTypeCount() > 0) {
                    prop.setNonTrivialBaseType(type);
                }
                continue;
            }
        }
        JsonSerializer<Object> ser = provider.findValueSerializer(type, prop);
        if (type.isContainerType()) {
            TypeSerializer typeSer = type.getContentType().getTypeHandler();
            // Do something with typeSer
        }
        prop = prop.withSerializer(ser);
        if (i < filteredCount) {
            if (w2 != null) {
                _filteredProps[i] = w2.withSerializer(ser);
            }
        }
        // Do something with prop
    }
}