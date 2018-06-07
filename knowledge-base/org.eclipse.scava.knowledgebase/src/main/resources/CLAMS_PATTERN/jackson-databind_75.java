{
    Bean bean;
    ObjectMapper mapper = ObjectMapper.builder()
    .addModule(new SerializerModifierModule(new RemovingModifier("a string")))
    .build();
    assertEquals("a string", mapper.writeValueAsString(bean));
}