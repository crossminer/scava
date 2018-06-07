{
    String value;
    final ObjectMapper MAPPER;
    CaseChangingStringWrapper w = MAPPER.readValue(aposToQuotes("a string"),
    CaseChangingStringWrapper.class);
    assertEquals("a string", w.value.get());
}