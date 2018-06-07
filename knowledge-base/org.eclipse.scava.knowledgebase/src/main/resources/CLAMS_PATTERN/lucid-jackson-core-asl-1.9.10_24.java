{
    CtorValueBean result = new ObjectMapper().readValue("a string", CtorValueBean.class);
    assertEquals("a string", result.toString());
}