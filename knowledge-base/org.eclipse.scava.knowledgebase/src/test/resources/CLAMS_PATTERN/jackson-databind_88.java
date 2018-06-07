{
    final static String CLASS_DESC;
    final ObjectMapper MAPPER;
    BeanDescription beanDesc = MAPPER.deserializationConfig().introspect(MAPPER.constructType(DocumentedBean.class));
    assertEquals(CLASS_DESC, beanDesc.findClassDescription());
}