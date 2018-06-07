{
    String propName;
    DeserializationContext ctxt;
    final protected Set<String> _ignorableProps;
    JsonParser p;
    Object beanOrClass;
    final protected boolean _ignoreAllUnknown;
    if (_ignoreAllUnknown) {
        p.skipChildren();
    }
    if (_ignorableProps != null && _ignorableProps.contains(propName)) {
        handleIgnoredProperty(p, ctxt, beanOrClass, propName);
    }
}