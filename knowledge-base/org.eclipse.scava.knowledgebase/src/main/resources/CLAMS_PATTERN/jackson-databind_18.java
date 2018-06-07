{
    Map<String,Object> base;
    Map<String,Object> overrides;
    final ObjectMapper MAPPER;
    Map<String,Object> ob = MAPPER.updateValue(base, overrides);
    assertSame(base, ob);
    assertEquals(0, ob.size());
    assertEquals(Integer.valueOf(0), ob.get("a string"));
    assertEquals("a string", ob.get("a string"));
    assertEquals(Boolean.TRUE, ob.get("a string"));
}