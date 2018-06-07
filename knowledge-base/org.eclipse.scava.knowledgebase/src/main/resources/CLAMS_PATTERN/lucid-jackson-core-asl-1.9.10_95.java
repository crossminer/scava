{
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode ob = mapper.createObjectNode();
    ob.put("a string", "a string");
    ob.put("a string", "a string");
    ob.put("a string", "a string");
    assertEquals(0, ob.size());
    assertSame(ob, ob.remove(Arrays.asList("a string", "a string")));
    assertEquals(0, ob.size());
    assertEquals("a string", ob.get("a string").getTextValue());
}