{
    int EXP_I;
    JsonParser jp = createParserUsingReader("a string"+EXP_I+"a string");
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
    assertEquals(JsonParser.NumberType.INT, jp.getNumberType());
    assertEquals("a string"+EXP_I, jp.getText());

    assertEquals(EXP_I, jp.getIntValue());
    assertEquals((long) EXP_I, jp.getLongValue());
    assertEquals((double) EXP_I, jp.getDoubleValue());
    assertEquals(BigDecimal.valueOf((long) EXP_I), jp.getDecimalValue());
}