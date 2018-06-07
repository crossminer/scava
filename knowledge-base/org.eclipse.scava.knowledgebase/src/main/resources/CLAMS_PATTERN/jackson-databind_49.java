{
    final protected JavaType _beanType;
    JsonDeserializer<Object> _delegateDeserializer;
    DeserializationContext ctxt;
    final ValueInstantiator _valueInstantiator;
    JsonParser p;
    PropertyBasedCreator _propertyBasedCreator;
    final JsonDeserializer<Object> delegateDeser = _delegateDeserializer();
    if (delegateDeser != null) {
        return _valueInstantiator.createUsingDelegate(ctxt,
        delegateDeser.deserialize(p, ctxt));
    }
    if (_propertyBasedCreator != null) {
        return _deserializeUsingPropertyBased(p, ctxt);
    }
    Class<?> raw = _beanType.getRawClass();
    if (ClassUtil.isNonStaticInnerClass(raw)) {
        return ctxt.handleMissingInstantiator(raw, null, p,
        "a string");
    }
    return ctxt.handleMissingInstantiator(raw, getValueInstantiator(), p,
    "a string");
}