{
    double barkVolume;
    String name;
    ObjectMapper mapper = new ObjectMapper();
    Animal animal = mapper.readValue("a string", Animal.class);
    assertEquals(Dog.class, animal.getClass());
    assertEquals("a string", animal.name);
    assertEquals(0, ((Dog) animal).barkVolume);
}