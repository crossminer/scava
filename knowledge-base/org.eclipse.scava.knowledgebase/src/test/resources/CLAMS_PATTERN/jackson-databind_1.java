{
    String[] strs;
    final ObjectMapper MAPPER;
    assertEquals("a string", MAPPER.writeValueAsString(new AtomicReference<String[]>(strs)));
}