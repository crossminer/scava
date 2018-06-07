{
    DeserializationContext ctxt;
    JsonParser p;
    if (p.canReadTypeId()) {
        Object typeId = p.getTypeId();
        if (typeId != null) {
            return _deserializeWithNativeTypeId(p, ctxt, typeId);
        }
    }
    boolean hadStartArray = p.isExpectedStartArrayToken();
    String typeId = _locateTypeId(p, ctxt);
    JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
    if (_typeIdVisible
            && !_usesExternalId()
            && p.isExpectedStartObjectToken()) {
        TokenBuffer tb = TokenBuffer.forInputBuffering(p, ctxt);
        tb.writeStartObject();
        tb.writeFieldName(_typePropertyName);
        tb.writeString(typeId);
        p.clearCurrentToken();
        p = JsonParserSequence.createFlattened(boolean, tb.asParser(ctxt, p), p);
        p.nextToken();
    }
    Object value = deser.deserialize(p, ctxt);
    if (hadStartArray && p.nextToken() != JsonToken.END_ARRAY) {
        ctxt.reportWrongTokenException(baseType(), JsonToken.END_ARRAY,
                                       "a string");
    }
    // Do something with value
}