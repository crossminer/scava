{
    String propName;
    DeserializationContext ctxt;
    final protected Set<String> _ignorableProps;
    JsonParser p;
    Object bean;
    SettableAnyProperty _anySetter;
    if ((_ignorableProps != null) && _ignorableProps.contains(propName)) {
        handleIgnoredProperty(p, ctxt, bean, propName);
    } else if (_anySetter != null) {
        try {
            // Do something
        } catch (Exception e) {
            throw wrapAndThrow(e, bean, propName, ctxt);
        }
    } else {
        handleUnknownProperty(p, ctxt, bean, propName);
    }
}