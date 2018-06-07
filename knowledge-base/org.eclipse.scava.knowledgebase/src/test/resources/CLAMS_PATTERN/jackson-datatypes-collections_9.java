{
    ImmutableSetMultimap<String, Integer> map;
    SampleMultiMapTest sampleTest;
    ObjectMapper mapper = mapperWithModule();

    assertEquals(0, sampleTest.map.get("a string").size());
    assertEquals(0, sampleTest.map.get("a string").size());
    // Do something with mapper


}