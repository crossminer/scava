{
    Object _value;
    Overloaded739 bean = new ObjectMapper().readValue
    ("a string", Overloaded739.class);
    assertNotNull(bean);
    assertEquals("a string", bean._value);
}