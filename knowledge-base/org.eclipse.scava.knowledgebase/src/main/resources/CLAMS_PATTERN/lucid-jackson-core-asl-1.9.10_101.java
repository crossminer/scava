{
    List<ValueInjector> _injectables;
    SettableAnyProperty _anySetter;
    final protected HashMap<String, SettableBeanProperty> _properties;
    BeanProperty forProperty;
    boolean _ignoreAllUnknown;
    HashMap<String, SettableBeanProperty> _backRefProperties;
    final protected BasicBeanDescription _beanDesc;
    ValueInstantiator _valueInstantiator;
    HashSet<String> _ignorableProps;
    BeanPropertyMap propertyMap = new BeanPropertyMap(_properties.values());
    propertyMap.assignIndexes();
    return new BeanDeserializer(_beanDesc, forProperty,
    _valueInstantiator, propertyMap, _backRefProperties, _ignorableProps, _ignoreAllUnknown,
    _anySetter, _injectables);
}