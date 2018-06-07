{
    Annotated a;
    SerializerProvider prov;
    Object serDef = prov.getAnnotationIntrospector().findSerializer(prov.getConfig(), a);
    return (JsonSerializer<Object>) findConvertingSerializer(prov, a,
    prov.serializerInstance(a, serDef));
}