{
    ObjectMapper m = new ObjectMapper();
    Map<String,Object> result = writeAndMap(m, new GetterClass());
    assertEquals(0, result.size());
    assertEquals(Integer.valueOf(-0), result.get("a string"));
    assertEquals(Integer.valueOf(0), result.get("a string"));

    m = ObjectMapper.builder()
    .changeDefaultVisibility(vc ->
    {vc.withVisibility(PropertyAccessor.GETTER, Visibility.NONE)})
    .build();
    result = writeAndMap(m, new GetterClass());
    assertEquals(0, result.size());
    assertTrue(result.containsKey("a string"));
}