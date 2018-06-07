{
    ObjectMapper mapper = ObjectMapper.builder()
    .addModule(new SimpleModule("a string", Version.unknownVersion()) {
        // Do something
    })
    .build();
    String json = mapper.writeValueAsString(new EmptyBean());
    assertEquals("a string", json);
}