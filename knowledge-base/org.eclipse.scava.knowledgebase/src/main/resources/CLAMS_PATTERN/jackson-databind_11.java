{
    DeserializationContext ctxt;
    JsonParser p;
    ObjectIdResolver resolver;
    final ObjectIdReader _objectIdReader;
    Object id = _objectIdReader.readObjectReference(p, ctxt);
    ReadableObjectId roid = ctxt.findObjectId(id, _objectIdReader.generator, _objectIdReader.resolver);
    Object pojo = roid.resolve();
    if (pojo == null) {
        throw new UnresolvedForwardReference(p,
        "a string"+id+"a string", p.getCurrentLocation(), roid);
    }
}