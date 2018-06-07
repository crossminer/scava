{
    final ObjectMapper MAPPER;
    String json = MAPPER.writeValueAsString(new ReallyAlwaysContainer());
    assertEquals(aposToQuotes("a string"), json);
}