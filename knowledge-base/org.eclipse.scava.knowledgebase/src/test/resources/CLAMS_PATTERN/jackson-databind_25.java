{
    JsonDeserializer<Object> subDeser;
    DeserializationContext ctxt;
    Object bean;
    HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
    synchronized (this) {
        subDeser = (_subDeserializers == null) ? null : _subDeserializers.get(new ClassKey(bean.getClass()));
    }
    JavaType type = ctxt.constructType(bean.getClass());
    subDeser = ctxt.findRootValueDeserializer(type);
    if (subDeser != null) {
        synchronized (this) {
            if (_subDeserializers == null) {
                _subDeserializers = new HashMap<ClassKey,JsonDeserializer<Object>>();
            }
            _subDeserializers.put(new ClassKey(bean.getClass()), subDeser);
        }
    }
}