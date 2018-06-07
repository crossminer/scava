{
    TypeSerializer _typeSerializer;
    final Object _suppressableValue;
    final SerializedString _name;
    final Object value;
    Object bean;
    PropertySerializerMap m;
    JsonGenerator gen;
    Class<?> cls;
    final static Object MARKER_FOR_EMPTY;
    JsonSerializer<Object> _nullSerializer;
    SerializerProvider prov;
    JsonSerializer<Object> ser;
    if (value == null) {
        if (_nullSerializer != null) {
            gen.writeFieldName(_name);
            _nullSerializer.serialize(null, gen, prov);
        }
    }
    if (ser == null) {
        ser = m.serializerFor(cls);
        if (ser == null) {
            ser = _findAndAddDynamic(m, cls, prov);
        }
    }
    if (_suppressableValue != null) {
        if (MARKER_FOR_EMPTY == _suppressableValue) {
            if (ser.isEmpty(prov, value)) {
                // Do something
            }
        } else
        }
    if (value == bean) {
        if (_handleSelfReference(bean, gen, prov, ser)) {
            // Do something
        }
    }
    gen.writeFieldName(_name);
    if (_typeSerializer == null) {
        ser.serialize(value, gen, prov);
    } else {
        ser.serializeWithType(value, gen, prov, _typeSerializer);
    }
}