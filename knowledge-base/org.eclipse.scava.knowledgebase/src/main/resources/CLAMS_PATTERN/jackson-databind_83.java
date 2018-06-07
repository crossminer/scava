{
    final int _x;
    final String _stuff;
    ObjectMapper mapper = ObjectMapper.builder()
    .injectableValues(new InjectableValues.Std()
    .addValue(String.class, "a string"))
    .build();
    InjectableXY bean = mapper.readValue(aposToQuotes("a string"),
    InjectableXY.class);
    assertEquals(0, bean._x);
    assertEquals(0, bean._y);
    assertEquals("a string", bean._stuff);
}