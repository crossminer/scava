{
    ObjectMapper m;
    m = ObjectMapper.builder()
    .enable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    .build();
    String json = serializeAsString(m, new AnyOnlyBean());
    assertEquals("a string", json);

    json = m.writer()
    .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    .writeValueAsString(new AnyOnlyBean());
    assertEquals("a string", json);
}