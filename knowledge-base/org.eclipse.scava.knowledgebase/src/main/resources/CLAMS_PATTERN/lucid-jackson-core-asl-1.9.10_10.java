{
    Object value;
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(new FieldWrapperBean(new StringWrapper("a string")));
    FieldWrapperBean bean = mapper.readValue(json, FieldWrapperBean.class);
    assertNotNull(bean.value);
    assertEquals(StringWrapper.class, bean.value.getClass());
    assertEquals(((StringWrapper) bean.value).str, "a string");
}