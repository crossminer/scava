{
    String id;
    String property;
    String ID1;
    Item value;
    String ID2;
    Map<String, Item> items;
    String json = aposToQuotes(
        "a string"+ID1+"a string"+ID2+"a string");

    ObjectMapper m = new ObjectMapper();
    Data data = m.readValue(json, Data.class);

    assertEquals(ID1, data.id);
    assertNotNull(data.items);
    assertEquals(0, data.items.size());
    assertNotNull(value);
    assertEquals("a string", value.property);
}