{
    JsonToken t;
    DeserializationContext ctxt;
    JsonParser p;
    if (t != null) {
        switch (t) {
        case VALUE_STRING:
            return deserializeFromString(p, ctxt);
        case VALUE_NUMBER_INT:
            return deserializeFromNumber(p, ctxt);
        case VALUE_NUMBER_FLOAT:
            return deserializeFromDouble(p, ctxt);
        case VALUE_EMBEDDED_OBJECT:
            return deserializeFromEmbedded(p, ctxt);
        case VALUE_TRUE:
        case VALUE_FALSE:
            return deserializeFromBoolean(p, ctxt);
        case VALUE_NULL:
            return deserializeFromNull(p, ctxt);
        case START_ARRAY:
            return deserializeFromArray(p, ctxt);
        case FIELD_NAME:
        case END_OBJECT:
            if (_vanillaProcessing) {
                return _vanillaDeserialize(p, ctxt, t);
            }
            if (_objectIdReader != null) {
                return deserializeWithObjectId(p, ctxt);
            }
            return deserializeFromObject(p, ctxt);
        default:
        }
    }
    return ctxt.handleUnexpectedToken(handledType(), p);
}