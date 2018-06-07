{
    SettableBeanProperty prop;
    PropertyValueBuffer buffer;
    Object builder;
    final DeserializationContext ctxt;
    int i;
    final JsonParser p;
    SettableBeanProperty creatorProp;
    final Class<?> activeView = _needViewProcesing ? ctxt.getActiveView() : null;
    for (; p.nextToken() != JsonToken.END_ARRAY; ++i) {
        if (prop == null) {
            p.skipChildren();
            continue;
        }
        if ((activeView != null) && !prop.visibleInView(activeView)) {
            p.skipChildren();
            continue;
        }
        if (builder != null) {
            try {
                builder = prop.deserializeSetAndReturn(p, ctxt, builder);
            } catch (Exception e) {
                throw wrapAndThrow(e, builder, prop.getName(), ctxt);
            }
            continue;
        }
        final String propName = prop.getName();
        if (creatorProp != null) {
            if (buffer.assignParameter(creatorProp, creatorProp.deserialize(p, ctxt))) {
                try {
                    // Do something
                } catch (Exception e) {
                    throw wrapAndThrow(e, _beanType.getRawClass(), propName, ctxt);
                }
                if (builder.getClass() != _beanType.getRawClass()) {
                    return ctxt.reportBadDefinition(_beanType, String.format(
                                                        "a string",
                                                        _beanType.getRawClass().getName(), builder.getClass().getName()));
                }
            }
            continue;
        }
        if (buffer.readIdProperty(propName)) {
            continue;
        }
        buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
    }

    if (builder == null) {
        try {
            // Do something
        } catch (Exception e) {
            return wrapInstantiationProblem(e, ctxt);
        }
    }
}