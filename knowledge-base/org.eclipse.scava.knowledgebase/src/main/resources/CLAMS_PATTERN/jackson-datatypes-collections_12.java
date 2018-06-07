{
    Unit base;
    String json;
    Optional<Unit> baseUnit;
    final ObjectMapper MAPPER;
    final Unit input = new Unit();
    input.link(input);
    Unit result = MAPPER.readValue(json, Unit.class);
    assertNotNull(result);
    assertNotNull(result.baseUnit);
    assertTrue(result.baseUnit.isPresent());
    assertSame(result, base);
}