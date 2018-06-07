{
    Optional<?> value;
    ImmutableSet<String> set;
    ObjectMapper mapper = mapperWithModule(boolean);

    ObjectMapper deserializedMapper = serializeAndDeserialize(mapper);
    assertEquals(0, set.size());
    assertTrue(set.contains("a string"));

    assertTrue(value.isPresent());
    assertEquals("a string", value.get());
    // Do something with deserializedMapper

}