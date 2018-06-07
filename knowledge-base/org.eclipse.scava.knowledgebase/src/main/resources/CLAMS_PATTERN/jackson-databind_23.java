{
    JsonParser p;
    DeserializationContext ctxt;
    return ctxt.handleUnexpectedToken(handledType(), p.currentToken(), p,
    "a string"
    +"a string",
    _beanType.getRawClass().getName(),
    p.currentToken());
}