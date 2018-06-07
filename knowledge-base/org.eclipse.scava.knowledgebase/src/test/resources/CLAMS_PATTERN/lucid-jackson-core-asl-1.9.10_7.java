{
    final AbstractTypeResolver[] _abstractTypeResolvers;
    KeyDeserializers additional;
    final KeyDeserializers[] _additionalKeyDeserializers;
    final ValueInstantiators[] _valueInstantiators;
    final BeanDeserializerModifier[] _modifiers;
    final Deserializers[] _additionalDeserializers;
    KeyDeserializers[] all = ArrayBuilders.insertInListNoDup(_additionalKeyDeserializers, additional);
    return new ConfigImpl(_additionalDeserializers, all, _modifiers,
    _abstractTypeResolvers, _valueInstantiators);
}