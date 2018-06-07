{
    final ObjectMapper MAPPER;
    assertEquals("a string", MAPPER.writeValueAsString(new AtomicBoolean(boolean)));
    assertEquals("a string", MAPPER.writeValueAsString(new AtomicBoolean(boolean)));
}