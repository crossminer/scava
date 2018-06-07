{
    FactoryValueBean result = new ObjectMapper().readValue("a string", FactoryValueBean.class);
    assertEquals("a string", result.toString());
}