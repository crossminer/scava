{
    ObjectMapper mapper = new ObjectMapper();
    AtomicInteger value = mapper.readValue("a string", AtomicInteger.class);
    assertEquals(0, value.get());
}