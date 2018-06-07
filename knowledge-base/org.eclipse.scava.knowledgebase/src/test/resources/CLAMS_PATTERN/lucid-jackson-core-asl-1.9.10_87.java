{
    Object value;
    JsonGenerator jgen;
    jgen.writeStartArray();
    jgen.writeString(_idResolver.idFromValue(value));
    jgen.writeStartObject();
}