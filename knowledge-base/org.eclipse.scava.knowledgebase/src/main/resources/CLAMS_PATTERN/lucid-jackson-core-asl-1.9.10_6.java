{
    Bean b;
    final String JSON;
    int a;
    ObjectMapper mapper = new ObjectMapper();
    Iterator<Bean> it = mapper.reader(Bean.class).readValues(JSON);

    assertTrue(it.hasNext());
    assertEquals(0, b.a);
    assertTrue(it.hasNext());
    assertEquals(0, b.a);
    assertFalse(it.hasNext());
}