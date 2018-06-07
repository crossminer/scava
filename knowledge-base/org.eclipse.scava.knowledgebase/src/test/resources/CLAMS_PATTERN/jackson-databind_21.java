{
    DeserializationContext ctxt;
    SettableBeanProperty prop;
    NameTransformer xf;
    if (prop != null) {
        String newName = xf.transform(prop.getName());
        prop = prop.withSimpleName(newName);
        JsonDeserializer<?> deser = prop.getValueDeserializer();
        if (deser != null) {
            @SuppressWarnings("a string")
            JsonDeserializer<Object> newDeser = (JsonDeserializer<Object>)
            deser.unwrappingDeserializer(ctxt, xf);
            if (newDeser != deser) {
                prop = prop.withValueDeserializer(newDeser);
                // Do something with prop
            }
        }
    }
}