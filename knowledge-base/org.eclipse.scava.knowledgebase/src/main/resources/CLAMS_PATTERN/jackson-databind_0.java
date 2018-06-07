{
    final boolean EXP;
    final ObjectMapper MAPPER;
    BooleanCreatorValue value = MAPPER.readValue(String.valueOf(EXP),
    BooleanCreatorValue.class);
    assertEquals(EXP, value.value);
}