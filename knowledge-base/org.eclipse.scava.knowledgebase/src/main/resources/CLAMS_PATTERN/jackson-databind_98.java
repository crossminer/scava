{
    AnnotatedParameter param;
    AnnotationIntrospector intr;
    if (param != null && intr != null) {
        PropertyName name = intr.findNameForDeserialization(param);
        String str = intr.findImplicitPropertyName(param);
        if (str != null && !str.isEmpty()) {
            return PropertyName.construct(str);
        }
        // Do something with name
    }
}