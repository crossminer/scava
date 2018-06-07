{
    final String json;
    final ObjectMapper MAPPER;
    ValueInterface value = MAPPER.readValue(json, ValueInterface.class);
    assertEquals(0, value.getX());
}