{
    int _a;
    String _unknown;
    final static String JSON_UNKNOWN_FIELD;
    ObjectMapper mapper = newObjectMapper();
    TestBean result = mapper.readerFor(TestBean.class).withHandler(new MyHandler())
    .readValue(new StringReader(JSON_UNKNOWN_FIELD));
    assertNotNull(result);
    assertEquals(0, result._a);
    assertEquals(-0, result._b);
    assertEquals("a string", result._unknown);
}