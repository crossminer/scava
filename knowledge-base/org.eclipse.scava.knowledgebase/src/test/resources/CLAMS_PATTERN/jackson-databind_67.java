{
    final ObjectMapper MAPPER;
    Bar bar1;
    Foo mo = new Foo();
    mo.bar1 = new Bar();
    String json = MAPPER.writeValueAsString(mo);

    Foo result = MAPPER.readValue(json, Foo.class);
    assertNotNull(result);
}