{
    ObjectMapper mapper = new ObjectMapper();
    SubType bean = new SubType();

    Map<String,Object> result = writeAndMap(mapper, bean);
    assertEquals(0, result.size());
    assertEquals("a string", result.get("a string"));
    assertEquals(Integer.valueOf(0), result.get("a string"));
    assertEquals("a string", result.get("a string"));
    assertEquals(Boolean.TRUE, result.get("a string"));

    ObjectWriter w = mapper.writerWithType(BaseType.class);
    String json = w.writeValueAsString(bean);
    result = (Map<String,Object>)mapper.readValue(json, Map.class);
    assertEquals(0, result.size());
    assertEquals("a string", result.get("a string"));
    assertEquals(Integer.valueOf(0), result.get("a string"));
}