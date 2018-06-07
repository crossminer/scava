{
    Parent parent;
    String name;
    SimpleTreeNode resultChild;
    SimpleTreeNode root = new SimpleTreeNode("a string");
    SimpleTreeNode child = new SimpleTreeNode("a string");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(root);

    SimpleTreeNode resultNode = mapper.readValue(json, SimpleTreeNode.class);
    assertEquals("a string", resultNode.name);
    assertNotNull(resultChild);
    assertEquals("a string", resultChild.name);
    assertSame(resultChild.parent, resultNode);
    // Do something with child

}