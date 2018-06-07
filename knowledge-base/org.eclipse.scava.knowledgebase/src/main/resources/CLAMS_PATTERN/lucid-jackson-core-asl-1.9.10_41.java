{
    byte[] data;
    BigDecimal in;
    ByteArrayOutputStream bo;
    SmileGenerator g = smileGenerator(bo, boolean);
    SmileParser p = _smileParser(data);
    assertToken(JsonToken.VALUE_NUMBER_FLOAT, p.nextToken());
    assertEquals(JsonParser.NumberType.BIG_DECIMAL, p.getNumberType());
    assertEquals(BigDecimal.class, p.getNumberValue().getClass());
    assertEquals(in, p.getDecimalValue());
    g = smileGenerator(bo, boolean);
    p = _smileParser(data);
    assertToken(JsonToken.START_ARRAY, p.nextToken());
    assertToken(JsonToken.VALUE_NUMBER_FLOAT, p.nextToken());
    assertToken(JsonToken.END_ARRAY, p.nextToken());
    assertNull(p.nextToken());
    // Do something with g
}