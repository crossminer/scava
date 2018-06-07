{
    final Method _anyGetter;
    SerializerProvider provider;
    Object value;
    JsonGenerator jgen;
    final MapSerializer _serializer;
    if (!(value instanceof Map<?,?>)) {
        throw new JsonMappingException("a string"+_anyGetter.getName()+"a string"
        +value.getClass().getName());
    }
    _serializer.serializeFields((Map<?,?>) value, jgen, provider);
}