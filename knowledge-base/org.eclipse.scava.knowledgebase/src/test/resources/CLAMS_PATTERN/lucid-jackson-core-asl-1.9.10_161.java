{
    AnnotatedMember am;
    XmlElements elems = findAnnotation(XmlElements.class, am, boolean, boolean, boolean);
    XmlElementRefs elemRefs = findAnnotation(XmlElementRefs.class, am, boolean, boolean, boolean);
    TypeResolverBuilder<?> b = new StdTypeResolverBuilder();
    b = b.init(JsonTypeInfo.Id.NAME, null);
    b = b.inclusion(JsonTypeInfo.As.WRAPPER_OBJECT);
    // Do something with elems

    // Do something with elemRefs

    // Do something with b
}