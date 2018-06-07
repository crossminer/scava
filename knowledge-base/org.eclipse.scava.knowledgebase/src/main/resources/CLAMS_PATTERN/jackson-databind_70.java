{
    Annotated ann;
    DeserializationContext ctxt;
    AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
    if (intr != null) {
        Object deserDef = intr.findDeserializer(ctxt.getConfig(), ann);
        if (deserDef != null) {
            return ctxt.deserializerInstance(ann, deserDef);
        }
    }
}