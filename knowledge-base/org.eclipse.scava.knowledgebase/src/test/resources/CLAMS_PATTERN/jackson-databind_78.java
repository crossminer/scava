{
    DeserializationContext ctxt;
    JsonParser p;
    Object rawId;
    JsonDeserializer<Object> idDeser;
    TokenBuffer buf = new TokenBuffer(p, ctxt);
    if (rawId instanceof String) {
        buf.writeString((String) rawId);
    } else if (rawId instanceof Long) {
        buf.writeNumber(((Long) rawId).longValue());
    } else if (rawId instanceof Integer) {
        buf.writeNumber(((Integer) rawId).intValue());
    } else {
        buf.writeObject(rawId);
    }
    JsonParser bufParser = buf.asParser();
    bufParser.nextToken();
    return idDeser.deserialize(bufParser, ctxt);
}