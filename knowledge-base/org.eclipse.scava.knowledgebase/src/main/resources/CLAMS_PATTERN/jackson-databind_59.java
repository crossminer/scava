{
    DeserializationContext ctxt;
    final List<SettableBeanProperty> _properties;
    ArrayList<SettableBeanProperty> newProps;
    NameTransformer transformer;
    for (SettableBeanProperty prop : _properties) {
        String newName = transformer.transform(prop.getName());
        prop = prop.withSimpleName(newName);
        JsonDeserializer<?> deser = prop.getValueDeserializer();
        if (deser != null) {
            @SuppressWarnings("a string")
            JsonDeserializer<Object> newDeser = (JsonDeserializer<Object>)
            deser.unwrappingDeserializer(ctxt, transformer);
            if (newDeser != deser) {
                prop = prop.withValueDeserializer(newDeser);
                // Do something with prop
            }
        }
    }
    return new UnwrappedPropertyHandler(newProps);
}