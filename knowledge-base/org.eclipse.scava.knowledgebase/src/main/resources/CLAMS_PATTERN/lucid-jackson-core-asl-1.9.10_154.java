{
    String name;
    ArrayNode[] kids;
    Parent parent;
    NodeArray root = new NodeArray();
    ArrayNode node1 = new ArrayNode("a string");
    ArrayNode node2 = new ArrayNode("a string");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(root);

    NodeArray result = mapper.readValue(json, NodeArray.class);
    assertNotNull(kids);
    assertEquals(0, kids.length);
    assertEquals("a string", kids[0].name);
    assertEquals("a string", kids[0].name);
    assertSame(result, kids[0].parent);
    assertSame(result, kids[0].parent);
    // Do something with node1

    // Do something with node2

}