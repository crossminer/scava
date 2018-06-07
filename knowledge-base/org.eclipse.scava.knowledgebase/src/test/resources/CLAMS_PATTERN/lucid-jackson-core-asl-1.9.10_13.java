{
    byte[] data;
    double value;
    ByteArrayOutputStream bo;
    SmileGenerator g = smileGenerator(bo, boolean);
    assertEquals(0, data.length);

    SmileParser p = _smileParser(data);
    assertToken(JsonToken.VALUE_NUMBER_FLOAT, p.nextToken());
    assertEquals(JsonParser.NumberType.DOUBLE, p.getNumberType());
    assertEquals(value, p.getDoubleValue());
    // Do something with g

}