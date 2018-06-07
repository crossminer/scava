{
    final protected Map<String, SettableBeanProperty> _properties;
    ObjectIdReader _objectIdReader;
    boolean _ignoreAllUnknown;
    HashMap<String, SettableBeanProperty> _backRefProperties;
    Collection<SettableBeanProperty> props;
    final protected BeanDescription _beanDesc;
    final protected DeserializationConfig _config;
    HashSet<String> _ignorableProps;
    _fixAccess(props);
    if (_objectIdReader != null) {
        props = _addIdProp(_properties,
        new ObjectIdValueProperty(_objectIdReader, PropertyMetadata.STD_REQUIRED));
    }
    BeanPropertyMap propertyMap = BeanPropertyMap.construct(props,
    _config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES),
    _collectAliases(props));

    boolean anyViews = !_config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
    if (!anyViews) {
        for (SettableBeanProperty prop : props) {
            if (prop.hasViews()) {
                break;
            }
        }
    }

    return new BeanDeserializer(this,
                                _beanDesc, propertyMap, _backRefProperties, _ignorableProps, _ignoreAllUnknown,
                                anyViews);
}