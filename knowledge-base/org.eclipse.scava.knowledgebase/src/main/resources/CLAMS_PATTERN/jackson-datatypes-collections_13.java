{
    Iterable<String> input;
    String json;
    final ObjectMapper MAPPER;
    assertEquals(aposToQuotes("a string"), json);

    json = MAPPER.writeValueAsString(new IterableWrapper(input));
    assertEquals(aposToQuotes("a string"), json);
}