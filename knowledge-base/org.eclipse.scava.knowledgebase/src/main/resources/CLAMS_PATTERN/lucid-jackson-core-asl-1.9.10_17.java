{
    ObjectMapper mapper = new ObjectMapper();
    AnnotationIntrospector intr = new JaxbAnnotationIntrospector();
    mapper.setAnnotationIntrospector(intr);
}