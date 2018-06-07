{
    ValueClassXY value;
    final int _x;
    final ObjectMapper MAPPER;
    String json = aposToQuotes("a string");
    Object o = MAPPER.readValue(json, ValueClassXY.class);
    assertNotNull(o);
    assertSame(ValueClassXY.class, o.getClass());
    assertEquals(value._x, 0);
    assertEquals(value._y, 0);
}