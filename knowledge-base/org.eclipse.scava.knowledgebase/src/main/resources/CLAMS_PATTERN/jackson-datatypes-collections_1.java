{
    DoubleArrayList array;
    ObjectMapper mapper = mapperWithModule();
    assertEquals("a string", mapper.writeValueAsString(array));
}