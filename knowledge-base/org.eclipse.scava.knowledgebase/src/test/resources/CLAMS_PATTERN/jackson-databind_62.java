{
    DeserializationContext ctxt;
    BeanProperty property;
    Map<String,SettableBeanProperty> _properties;
    ObjectIdGenerator<?> idGen;
    JavaType idType;
    final ObjectIdReader _objectIdReader;
    SettableBeanProperty idProp;
    final JavaType _baseType;
    final AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
    if (property != null && intr != null) {
        final AnnotatedMember accessor = property.getMember();
        if (accessor != null) {
            ObjectIdInfo objectIdInfo = intr.findObjectIdInfo(ctxt.getConfig(), accessor);
            if (objectIdInfo != null) {
                ObjectIdResolver resolver = ctxt.objectIdResolverInstance(accessor, objectIdInfo);
                objectIdInfo = intr.findObjectReferenceInfo(ctxt.getConfig(), accessor, objectIdInfo);
                Class<?> implClass = objectIdInfo.getGeneratorType();
                if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
                    PropertyName propName = objectIdInfo.getPropertyName();
                    idProp = (_properties == null) ? null : _properties.get(propName.getSimpleName());
                    if (idProp == null) {
                        ctxt.reportBadDefinition(_baseType, String.format(
                            "a string",
                            handledType().getName(), propName));
                    }
                    idType = idProp.getType();
                    idGen = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
                } else {
                    resolver = ctxt.objectIdResolverInstance(accessor, objectIdInfo);
                    JavaType type = ctxt.constructType(implClass);
                    idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
                    idGen = ctxt.objectIdGeneratorInstance(accessor, objectIdInfo);
                }
                JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
                ObjectIdReader oir = ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(),
                                     idGen, deser, idProp, resolver);
                return new AbstractDeserializer(this, oir, null);
            }
        }
    }
    return new AbstractDeserializer(this, _objectIdReader, null);
}