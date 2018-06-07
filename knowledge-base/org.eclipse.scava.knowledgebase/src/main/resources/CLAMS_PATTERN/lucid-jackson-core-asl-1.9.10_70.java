{
    ObjectMapper mapper = getJaxbMapper();
    assertEquals("a string", serializeAsString(mapper, new AlphaBean2()));
}