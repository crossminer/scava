{
    String name;
    byte[] data = _smileDoc("a string"+quote(name)+"a string");
    SmileParser p = _smileParser(new ByteArrayInputStream(data));
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.START_OBJECT, p.nextToken());
    assertToken(JsonToken.FIELD_NAME, p.nextToken());
    assertEquals(name, p.getCurrentName());
    assertToken(JsonToken.VALUE_NUMBER_INT, p.nextToken());
    assertEquals(0, p.getIntValue());
    assertEquals(name, p.getCurrentName());
    assertToken(JsonToken.END_OBJECT, p.nextToken());
    assertNull(p.nextToken());
}