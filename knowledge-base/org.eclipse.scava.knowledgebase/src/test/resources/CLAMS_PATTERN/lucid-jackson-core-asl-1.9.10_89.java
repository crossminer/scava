{
    String name;
    Class<?> refType;
    Annotated a;
    XmlElements elems = findAnnotation(XmlElements.class, a, boolean, boolean, boolean);
    if (elems != null) {
        ArrayList<NamedType> result = new ArrayList<NamedType>();
        for (XmlElement elem : elems.value()) {
            result.add(new NamedType(elem.type(), name));
        }
    } else {
        XmlElementRefs elemRefs = findAnnotation(XmlElementRefs.class, a, boolean, boolean, boolean);
        if (elemRefs != null) {
            ArrayList<NamedType> result = new ArrayList<NamedType>();
            for (XmlElementRef elemRef : elemRefs.value()) {
                if (!JAXBElement.class.isAssignableFrom(refType)) {
                    result.add(new NamedType(refType, name));
                }
            }
        }
    }
}