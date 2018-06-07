{
    ObjectMapper mapper = ObjectMapper.builder()
    .disable(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS)
    .build();
    try {
        System.setSecurityManager(new CauseBlockingSecurityManager());
        _testCauseOfThrowableIgnoral(mapper);
    } finally {
        // Do something
    }
}