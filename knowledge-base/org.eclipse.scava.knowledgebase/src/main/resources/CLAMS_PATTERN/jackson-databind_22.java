{
    ObjectMapper mapper = ObjectMapper.builder()
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .build();
    _testIgnorals(mapper);
}