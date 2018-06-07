{
    String value;
    ObjectMapper mapper = ObjectMapper.builder()
    .disable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)
    .build();

    try {
        mapper.readValue("a string"+value+"a string", String.class);
        fail("a string");
    } catch (MismatchedInputException exp) {
        verifyException(exp, "a string");
        verifyException(exp, "a string");
    }

    mapper = ObjectMapper.builder()
    .enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)
    .build();

    try {
        mapper.readValue("a string"+value+"a string"+value+"a string", String.class);
        fail("a string");
    } catch (MismatchedInputException exp) {
        verifyException(exp, "a string");
    }
    String result = mapper.readValue("a string"+value+"a string", String.class);
    assertEquals(value, result);
}