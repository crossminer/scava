{
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, boolean);
    String json = mapper.writeValueAsString(new WithRootName());
    assertEquals("a string", json);
}