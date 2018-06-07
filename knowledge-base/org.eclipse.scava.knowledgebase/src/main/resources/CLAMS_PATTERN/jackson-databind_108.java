{
    DeserializationContext ctxt;
    TokenBuffer tb;
    JsonParser p;
    if (p.canReadTypeId()) {
        Object typeId = p.getTypeId();
        if (typeId != null) {
            return _deserializeWithNativeTypeId(p, ctxt, typeId);
        }
    }

    JsonToken t = p.currentToken();
    if (t == JsonToken.START_OBJECT) {
        t = p.nextToken();
    } else if (t != JsonToken.FIELD_NAME) {
        return _deserializeTypedUsingDefaultImpl(p, ctxt, null);
    }
    for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
        String name = p.currentName();
        p.nextToken();
        if (name.equals(_typePropertyName)) {
            return _deserializeTypedForId(p, ctxt, tb);
        }
        if (tb == null) {
            tb = new TokenBuffer(p, ctxt);
        }
        tb.writeFieldName(name);
        tb.copyCurrentStructure(p);
    }
    return _deserializeTypedUsingDefaultImpl(p, ctxt, tb);
}