{
    final ObjectMapper MAPPER;
    String json = MAPPER.writeValueAsString(new NotEvenAnyBean());
    assertEquals(aposToQuotes("a string"), json);
}