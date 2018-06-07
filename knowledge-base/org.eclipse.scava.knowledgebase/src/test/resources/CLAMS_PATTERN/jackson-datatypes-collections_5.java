{
    OptionalData data;
    Optional<String> myString;
    assertTrue(data.myString.isPresent());
    assertEquals("a string", data.myString.get());
}