{
    final protected Object _propertyFilterId;
    final protected BeanPropertyWriter[] _props;
    JavaType typeHint;
    final BeanPropertyWriter[] props;
    JsonFormatVisitorWrapper visitor;
    final protected BeanPropertyWriter[] _filteredProps;
    BeanPropertyWriter prop;
    JsonObjectFormatVisitor objectVisitor = visitor.expectObjectFormat(typeHint);
    final SerializerProvider provider = visitor.getProvider();
    if (_propertyFilterId != null) {
        PropertyFilter filter = findPropertyFilter(visitor.getProvider(),
        _propertyFilterId, null);
        for (int i = 0, end = _props.length; i < end; ++i) {
            filter.depositSchemaProperty(_props[i], objectVisitor, provider);
        }
    } else {
        Class<?> view = ((_filteredProps == null) || (provider == null))
                        ? null : provider.getActiveView();
        for (int i = 0, end = props.length; i < end; ++i) {
            if (prop != null) {
                prop.depositSchemaProperty(objectVisitor, provider);
            }
        }
        // Do something with view
    }
}