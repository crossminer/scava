{
    T value;
    final ObjectMapper MAPPER;
    MergedX<Object[]> input = new MergedX<Object[]>(new Object[] {
        "a string"
    });
    final JavaType type = MAPPER.getTypeFactory().constructType(new TypeReference<MergedX<Object[]>>() {
        // Do something
    });
    MergedX<Object[]> result = MAPPER.readerFor(type)
    .withValueToUpdate(input)
    .readValue(aposToQuotes("a string"));
    assertSame(input, result);
    assertEquals(0, result.value.length);
    assertEquals("a string", result.value[0]);
    assertEquals("a string", result.value[0]);

    result = MAPPER.readerFor(type)
    .with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    .withValueToUpdate(input)
    .readValue(aposToQuotes("a string"));
    assertSame(input, result);
    assertEquals(0, result.value.length);
    assertEquals("a string", result.value[0]);
    assertEquals("a string", result.value[0]);
    assertEquals("a string", result.value[0]);
}