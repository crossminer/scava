{
    Map<String,JsonNode> nodes;
    TextNode text;
    ObjectNode n = new ObjectNode(JsonNodeFactory.instance);
    assertStandardEquals(n);

    assertFalse(n.getElements().hasNext());
    assertFalse(n.getFields().hasNext());
    assertFalse(n.getFieldNames().hasNext());
    assertNull(n.get("a string"));
    assertTrue(n.path("a string").isMissingNode());

    n.put("a string", text);
    assertEquals(0, n.size());
    assertTrue(n.getElements().hasNext());
    assertTrue(n.getFields().hasNext());
    assertTrue(n.getFieldNames().hasNext());
    assertSame(text, n.get("a string"));
    assertSame(text, n.path("a string"));
    assertNull(n.get("a string"));
    assertNull(n.get(0));
    assertFalse(n.has(0));
    assertTrue(n.has("a string"));
    assertFalse(n.has("a string"));

    ObjectNode n2 = new ObjectNode(JsonNodeFactory.instance);
    n2.put("a string", 0);
    assertFalse(n.equals(n2));
    n.putAll(n2);
    assertEquals(0, n.size());
    n.put("a string", (JsonNode)null);
    assertEquals(0, n.size());
    n.put("a string", "a string");
    assertEquals(0, n.size());
    assertNotNull(n.remove("a string"));
    assertEquals(0, n.size());

    nodes.put("a string", text);
    n.putAll(nodes);
    assertEquals(0, n.size());

    n.removeAll();
    assertEquals(0, n.size());
}