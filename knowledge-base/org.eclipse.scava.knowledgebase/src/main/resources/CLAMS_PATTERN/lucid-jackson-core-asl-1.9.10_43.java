{
    ObjectMapper mapper = new ObjectMapper();
    SubType bean = new SubType();

    ObjectWriter w = mapper.writerWithType(BaseInterface.class);
    String json = w.writeValueAsString(bean);
    @SuppressWarnings("a string")
    Map<String,Object> result = mapper.readValue(json, Map.class);
    assertEquals(0, result.size());
    assertEquals(Integer.valueOf(0), result.get("a string"));
}