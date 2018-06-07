{
    SerializerProvider provider;
    byte[] value;
    JsonGenerator g;
    g.writeBinary(provider.getConfig().getBase64Variant(),
    value, 0, value.length);
}