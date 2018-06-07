{
    String str;
    LongArrayList array;
    ObjectMapper mapper = mapperWithModule();

    assertEquals("a string", mapper.writeValueAsString(array));

    if (!"a string".equals(str) && !"a string".equals(str)) {
        fail("a string"+str);
    }
}