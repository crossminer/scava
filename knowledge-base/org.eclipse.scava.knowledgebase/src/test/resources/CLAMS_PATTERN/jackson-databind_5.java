{
    final String json = aposToQuotes("a string");

    final ObjectMapper mapper = new ObjectMapper();
    Animal animal = mapper.readValue(json, Animal.class);
    assertEquals(0, animal.getId());
    assertNotNull(animal.getName());
    assertEquals("a string", animal.getName().getFirst());
    assertEquals("a string", animal.getName().getLast());
    assertEquals(0, animal.getAge());
    assertEquals(boolean, animal.isAlive());
}