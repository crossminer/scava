{
    SettableBeanProperty prop;
    final int propCount;
    Object builder;
    DeserializationContext ctxt;
    JsonParser p;
    if (_nonStandardCreation) {
        return deserializeFromObjectUsingNonDefault(p, ctxt);
    }
    if (_injectables != null) {
        injectValues(ctxt, builder);
    }
    Class<?> activeView = _needViewProcesing ? ctxt.getActiveView() : null;
    while (boolean) {
        if (p.nextToken() == JsonToken.END_ARRAY) {
            // Do something
        }
        if (prop != null) {
            if (activeView == null || prop.visibleInView(activeView)) {
                try {
                    prop.deserializeSetAndReturn(p, ctxt, builder);
                } catch (Exception e) {
                    throw wrapAndThrow(e, builder, prop.getName(), ctxt);
                }
                continue;
            }
        }
        p.skipChildren();
    }
    if (!_ignoreAllUnknown && ctxt.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
        ctxt.reportWrongTokenException(this, JsonToken.END_ARRAY,
                                       "a string",
                                       propCount);
    }
    while (p.nextToken() != JsonToken.END_ARRAY) {
        p.skipChildren();
    }
}