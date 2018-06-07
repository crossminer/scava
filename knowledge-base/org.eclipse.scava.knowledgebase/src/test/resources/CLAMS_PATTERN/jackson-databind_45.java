{
    DeserializationContext ctxt;
    SettableBeanProperty prop;
    JsonFormat.Value format;
    final protected JavaType _beanType;
    JavaType referredType;
    String refName = prop.getManagedReferenceName();
    JsonDeserializer<?> valueDeser = prop.getValueDeserializer();
    SettableBeanProperty backProp = valueDeser.findBackReference(refName);
    if (backProp == null) {
        ctxt.reportBadDefinition(_beanType, String.format(
            "a string",
            refName, prop.getType()));
    }
    JavaType backRefType = backProp.getType();
    boolean isContainer = prop.getType().isContainerType();
    if (!backRefType.getRawClass().isAssignableFrom(referredType.getRawClass())) {
        ctxt.reportBadDefinition(_beanType, String.format(
            "a string",
            refName, backRefType.getRawClass().getName(),
            referredType.getRawClass().getName()));
    }
    // Do something with isContainer
}