{
    SerializerProvider provider;
    return createSchemaNode(provider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)
    ? "a string" : "a string", boolean);
}