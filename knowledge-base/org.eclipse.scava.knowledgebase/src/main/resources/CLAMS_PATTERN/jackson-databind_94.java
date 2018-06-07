{
    DeserializationContext ctxt;
    List<BeanPropertyDefinition> propDefsIn;
    HashMap<Class<?>,Boolean> ignoredTypes;
    for (BeanPropertyDefinition property : propDefsIn) {
        String name = property.getName();
        if (!property.hasConstructorParameter()) {
            Class<?> rawPropertyType = property.getRawPrimaryType();
            if ((rawPropertyType != null)
            && isIgnorableType(ctxt.getConfig(), property, rawPropertyType, ignoredTypes)) {
                continue;
            }
        }
        // Do something with name
    }
}