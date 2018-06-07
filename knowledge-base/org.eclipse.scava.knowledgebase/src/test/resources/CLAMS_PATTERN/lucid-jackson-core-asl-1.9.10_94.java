{
    final EnumValues _values;
    SerializerProvider provider;
    Enum<?> en;
    JsonGenerator jgen;
    if (provider.isEnabled(SerializationConfig.Feature.WRITE_ENUMS_USING_INDEX)) {
        jgen.writeNumber(en.ordinal());
    }
    jgen.writeString(_values.serializedValueFor(en));
}