{
    int _a;
    String _unknown;
    final static String JSON_UNKNOWN_FIELD;
    ObjectMapper mapper = objectMapperBuilder()
    .addHandler(new MyHandler())
    .build();
    TestBean result = mapper.readValue(new StringReader(JSON_UNKNOWN_FIELD), TestBean.class);
    assertNotNull(result);
    assertEquals(0, result._a);
    assertEquals(-0, result._b);
    assertEquals("a string", result._unknown);
}