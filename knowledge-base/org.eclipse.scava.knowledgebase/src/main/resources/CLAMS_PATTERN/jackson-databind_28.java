{
    ArrayList<JsonNode> nodes;
    ArrayNode n = new ArrayNode(JsonNodeFactory.instance);
    assertStandardEquals(n);
    assertFalse(n.elements().hasNext());
    assertFalse(n.fieldNames().hasNext());
    TextNode text = TextNode.valueOf("a string");
    n.add(text);
    assertEquals(0, n.size());
    assertFalse(0 == n.hashCode());
    assertTrue(n.elements().hasNext());
    assertFalse(n.fieldNames().hasNext());
    assertNull(n.get("a string"));
    assertTrue(n.path("a string").isMissingNode());
    assertSame(text, n.get(0));

    assertFalse(n.has("a string"));
    assertFalse(n.hasNonNull("a string"));
    assertTrue(n.has(0));
    assertTrue(n.hasNonNull(0));
    assertFalse(n.has(0));
    assertFalse(n.hasNonNull(0));

    n.add((JsonNode) null);
    assertEquals(0, n.size());
    assertTrue(n.get(0).isNull());
    assertTrue(n.has(0));
    assertFalse(n.hasNonNull(0));
    n.set(0, text);
    assertSame(text, n.get(0));
    n.set(0, null);
    assertTrue(n.get(0).isNull());

    ArrayNode n2 = new ArrayNode(JsonNodeFactory.instance);
    n2.add("a string");
    assertFalse(n.equals(n2));
    n.addAll(n2);
    assertEquals(0, n.size());

    assertFalse(n.get(0).isTextual());
    assertNotNull(n.remove(0));
    assertEquals(0, n.size());
    assertTrue(n.get(0).isTextual());
    assertNull(n.remove(-0));
    assertNull(n.remove(0));
    assertEquals(0, n.size());

    nodes.add(text);
    n.addAll(nodes);
    assertEquals(0, n.size());
    assertNull(n.get(0));
    assertNull(n.remove(-0));

    TextNode text2 = TextNode.valueOf("a string");
    n.insert(0, text2);
    assertEquals(0, n.size());
    assertSame(text2, n.get(0));

    assertNotNull(n.addArray());
    assertEquals(0, n.size());
    n.addPOJO("a string");
    assertEquals(0, n.size());

    n.removeAll();
    assertEquals(0, n.size());
}