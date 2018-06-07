{
    final SerializerFactoryConfig _factoryConfig;
    Serializers additional;
    return withConfig(_factoryConfig.withAdditionalSerializers(additional));
}