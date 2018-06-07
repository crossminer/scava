{
    BeanProperty property;
    DeserializationConfig config;
    Annotated ann;
    Object deserDef = config.getAnnotationIntrospector().findDeserializer(ann);
    if (deserDef != null) {
        return _constructDeserializer(config, ann, property, deserDef);
    }
}