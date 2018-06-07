{
    final Serializers[] _additionalSerializers;
    final Serializers[] _additionalKeySerializers;
    Serializers additional;
    final BeanSerializerModifier[] _modifiers;
    Serializers[] all = ArrayBuilders.insertInListNoDup(_additionalKeySerializers, additional);
    return new ConfigImpl(_additionalSerializers, all, _modifiers);
}