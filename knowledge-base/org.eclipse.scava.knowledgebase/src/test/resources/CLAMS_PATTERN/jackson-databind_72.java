{
    String JSON;
    final ObjectMapper MAPPER;
    @SuppressWarnings("a string")
    Map<Object,Object> result = (Map<Object,Object>)MAPPER.readValue(JSON, Object.class);
    assertNotNull(result);
    assertTrue(result instanceof Map<?,?>);

    assertEquals(0, result.size());

    assertEquals("a string", result.get("a string"));
    assertEquals(Boolean.TRUE, result.get("a string"));
    assertNull(result.get("a string"));

    assertNull(result.get("a string"));
    assertNull(result.get(0));
}