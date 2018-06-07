{
    DeserializationContext ctxt;
    JsonParser p;
    TypeDeserializer typeDeserializer;
    switch (p.currentTokenId()) {
    case JsonTokenId.ID_START_ARRAY:
    case JsonTokenId.ID_START_OBJECT:
    case JsonTokenId.ID_FIELD_NAME:
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    case JsonTokenId.ID_STRING:
        return p.getText();
    case JsonTokenId.ID_NUMBER_INT:
        if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
            // Do something
        }
        return p.getNumberValue();
    case JsonTokenId.ID_NUMBER_FLOAT:
        if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            return p.getDecimalValue();
        }
        return p.getNumberValue();
    case JsonTokenId.ID_TRUE:
    case JsonTokenId.ID_FALSE:
    case JsonTokenId.ID_EMBEDDED_OBJECT:
        return p.getEmbeddedObject();
    case JsonTokenId.ID_NULL:
    default:
    }
    return ctxt.handleUnexpectedToken(Object.class, p);
}