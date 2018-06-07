{
    final String json;
    final ObjectMapper MAPPER;
    try {
        MAPPER.readValue(json, ValueClassWrongBuildType.class);
        fail("a string");
    } catch (InvalidDefinitionException e) {
        verifyException(e, "a string");
        verifyException(e, "a string");
    }
}