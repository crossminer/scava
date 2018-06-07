{
    BasicBeanDescription beanDesc;
    ObjectMapper mapper = new ObjectMapper();
    beanDesc = mapper.getSerializationConfig().introspect(mapper.constructType(Issue701Bean.class));
    assertNotNull(beanDesc);
    beanDesc = mapper.getDeserializationConfig().introspect(mapper.constructType(Issue701Bean.class));
    assertNotNull(beanDesc);
}