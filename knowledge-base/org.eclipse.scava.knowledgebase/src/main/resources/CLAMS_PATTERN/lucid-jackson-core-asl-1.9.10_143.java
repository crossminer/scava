{
    SerializerProvider provider;
    Object bean;
    TypeSerializer typeSer;
    final protected Object _propertyFilterId;
    JsonGenerator jgen;
    typeSer.writeTypePrefixForObject(bean, jgen);
    if (_propertyFilterId != null) {
        serializeFieldsFiltered(bean, jgen, provider);
    } else {
        serializeFields(bean, jgen, provider);
    }
    typeSer.writeTypeSuffixForObject(bean, jgen);
}