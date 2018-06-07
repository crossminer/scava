{
    final ValueInstantiator _valueInstantiator;
    final protected BeanPropertyMap _beanProperties;
    ExternalTypeHandler.Builder extTypes;
    DeserializationConfig config;
    DeserializerProvider provider;
    final PropertyBasedCreator _propertyBasedCreator;
    final protected JavaType _beanType;
    SettableAnyProperty _anySetter;
    SettableBeanProperty origProp;
    final protected AnnotatedClass _forClass;
    JsonDeserializer<Object> _delegateDeserializer;
    Iterator<SettableBeanProperty> it = _beanProperties.allProperties();
    while (it.hasNext()) {
        if (!prop.hasValueDeserializer()) {
            prop = prop.withValueDeserializer(findDeserializer(config, provider, prop.getType(), prop));
        }
        prop = _resolveManagedReferenceProperty(config, prop);
        SettableBeanProperty u = _resolveUnwrappedProperty(config, prop);
        prop = _resolveInnerClassValuedProperty(config, prop);
        if (prop != origProp) {
            _beanProperties.replace(prop);
        }
        if (prop.hasValueTypeDeserializer()) {
            TypeDeserializer typeDeser = prop.getValueTypeDeserializer();
            if (typeDeser.getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
                extTypes.addExternal(prop, typeDeser.getPropertyName());
                _beanProperties.remove(prop);
            }
        }
        // Do something with u
    }

    if (_anySetter != null && !_anySetter.hasValueDeserializer()) {
        _anySetter = _anySetter.withValueDeserializer(findDeserializer(config, provider, _anySetter.getType(), _anySetter.getProperty()));
        // Do something with _anySetter
    }

    if (_valueInstantiator.canCreateUsingDelegate()) {
        JavaType delegateType = _valueInstantiator.getDelegateType();
        if (delegateType == null) {
            throw new IllegalArgumentException("a string"+_beanType
                                               +"a string"+_valueInstantiator.getClass().getName()
                                               +"a string");
        }
        AnnotatedWithParams delegateCreator = _valueInstantiator.getDelegateCreator();
        BeanProperty.Std property = new BeanProperty.Std(null,
                delegateType, _forClass.getAnnotations(), delegateCreator);
        _delegateDeserializer = findDeserializer(config, provider, delegateType, property);
        // Do something with _delegateDeserializer
    }
    if (_propertyBasedCreator != null) {
        for (SettableBeanProperty prop : _propertyBasedCreator.getCreatorProperties()) {
            if (!prop.hasValueDeserializer()) {
                _propertyBasedCreator.assignDeserializer(prop,
                        findDeserializer(config, provider, prop.getType(), prop));
            }
        }
    }
}