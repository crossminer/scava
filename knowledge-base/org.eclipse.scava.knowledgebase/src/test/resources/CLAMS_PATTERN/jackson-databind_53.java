{
    SerializerProvider provider;
    Object value;
    JsonGenerator gen;
    if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
        failForEmpty(provider, value);
    }
    gen.writeStartObject();
    gen.writeEndObject();
}