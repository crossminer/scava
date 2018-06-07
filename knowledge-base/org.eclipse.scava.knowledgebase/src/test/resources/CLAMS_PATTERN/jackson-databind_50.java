{
    Object value;
    MyContainer<?> myc;
    String json;
    final List<T> ts;
    SimpleModule module = new SimpleModule().addAbstractTypeMapping(IContainer.class, MyContainer.class);
    ObjectMapper mapper = ObjectMapper.builder()
    .addModule(module)
    .build();
    final Object o = mapper.readValue(json,
    mapper.getTypeFactory().constructParametricType(IContainer.class, MyObject.class));
    assertEquals(MyContainer.class, o.getClass());
    assertEquals(0, myc.ts.size());
    assertEquals(MyObject.class, value.getClass());
}