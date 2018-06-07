{
    final int _x;
    final ObjectMapper MAPPER;
    final String json = aposToQuotes("a string");
    CreatorValueXY resultX = MAPPER.readerFor(CreatorValueXY.class)
    .withView(ViewX.class)
    .readValue(json);
    assertEquals(0, resultX._x);
    assertEquals(0, resultX._y);

    CreatorValueXY resultY = MAPPER.readerFor(CreatorValueXY.class)
    .withView(ViewY.class)
    .readValue(json);
    assertEquals(0, resultY._x);
    assertEquals(0, resultY._y);
}