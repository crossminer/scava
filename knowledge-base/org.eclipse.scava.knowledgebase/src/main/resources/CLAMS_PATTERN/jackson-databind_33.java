{
    final ObjectMapper MAPPER;
    Bean bean = new Bean("a string", "a string");
    MAPPER.readerFor(Bean.class).withValueToUpdate(bean).readValue("a string");
    assertEquals("a string", bean.getA());
    assertEquals("a string", bean.getB());
}