{
    final DeserializerFactoryConfig _factoryConfig;
    Deserializers additional;
    return withConfig(_factoryConfig.withAdditionalDeserializers(additional));
}