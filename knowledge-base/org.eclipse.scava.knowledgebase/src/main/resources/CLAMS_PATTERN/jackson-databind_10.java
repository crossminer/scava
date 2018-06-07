{
    final String json = aposToQuotes("a string");

    final ObjectMapper mapper = new ObjectMapper();
    Person person = mapper.readValue(json, Person.class);
    assertEquals(0, person.getId());
    assertNotNull(person.getName());
    assertEquals("a string", person.getName().getFirst());
    assertEquals("a string", person.getName().getLast());
    assertEquals(0, person.getAge());
    assertEquals(boolean, person.isAlive());
}