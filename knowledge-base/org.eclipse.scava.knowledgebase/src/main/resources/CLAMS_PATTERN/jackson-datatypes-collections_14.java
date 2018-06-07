{
    ImmutableMap<Integer,Boolean> map;
    assertEquals(0, map.size());
    assertEquals(Boolean.TRUE, map.get(Integer.valueOf(0)));
    assertEquals(Boolean.FALSE, map.get(Integer.valueOf(0)));

    assertNotNull(map);
    assertEquals(0, map.size());

    assertEquals(0, map.size());
}