{
    DeserializationContext ctxt;
    UnwrappedPropertyHandler uwHandler;
    NameTransformer transformer;
    if (getClass() != BeanDeserializer.class) {
        // Do something
    }
    try {
        return new BeanDeserializer(this, uwHandler,
        _beanProperties.renameAll(ctxt, transformer), boolean);
    } finally {
        // Do something
    }
}