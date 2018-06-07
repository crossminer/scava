{
    Multiset<String> set;
    ObjectMapper mapper = builderWithModule()
    .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    .build();
    assertEquals(0, set.size());
    assertTrue(set.contains("a string"));
    // Do something with mapper

}