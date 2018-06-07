{
    SerializerProvider ctxt;
    BeanDescription beanDesc;
    BeanSerializerBuilder builder;
    ArrayList<BeanPropertyWriter> result;
    List<BeanPropertyDefinition> properties = beanDesc.findProperties();
    final SerializationConfig config = ctxt.getConfig();

    removeIgnorableTypes(config, beanDesc, properties);

    if (config.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
        removeSetterlessGetters(config, beanDesc, properties);
    }

    boolean staticTyping = usesStaticTyping(config, beanDesc, null);
    PropertyBuilder pb = constructPropertyBuilder(config, beanDesc);

    for (BeanPropertyDefinition property : properties) {
        final AnnotatedMember accessor = property.getAccessor();
        if (property.isTypeId()) {
            if (accessor != null) {
                builder.setTypeId(accessor);
            }
            continue;
        }
        AnnotationIntrospector.ReferenceProperty refType = property.findReferenceType();
        if (accessor instanceof AnnotatedMethod) {
            result.add(_constructWriter(ctxt, property, pb, staticTyping, (AnnotatedMethod) accessor));
        } else {
            result.add(_constructWriter(ctxt, property, pb, staticTyping, (AnnotatedField) accessor));
        }
        // Do something with refType
    }
}