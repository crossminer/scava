{
    byte[] data = _smileDoc("a string");
    SmileParser p = _smileParser(data);
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.START_OBJECT, p.nextToken());
    assertToken(JsonToken.FIELD_NAME, p.nextToken());
    assertEquals("a string", p.getCurrentName());
    assertToken(JsonToken.VALUE_TRUE, p.nextToken());
    assertToken(JsonToken.END_OBJECT, p.nextToken());
    assertNull(p.nextToken());
    p.close();

    data = _smileDoc("a string");
    p = _smileParser(data);
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.START_OBJECT, p.nextToken());
    assertToken(JsonToken.FIELD_NAME, p.nextToken());
    assertEquals("a string", p.getCurrentName());
    assertToken(JsonToken.VALUE_STRING, p.nextToken());
    assertEquals("a string", p.getText());
    assertToken(JsonToken.END_OBJECT, p.nextToken());
    assertNull(p.nextToken());
    p.close();

    data = _smileDoc("a string");
    p = _smileParser(data);
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.START_OBJECT, p.nextToken());
    assertToken(JsonToken.FIELD_NAME, p.nextToken());
    assertEquals("a string", p.getCurrentName());
    assertToken(JsonToken.VALUE_STRING, p.nextToken());
    assertEquals("a string", p.getText());
    assertToken(JsonToken.FIELD_NAME, p.nextToken());
    assertEquals("a string", p.getCurrentName());
    assertToken(JsonToken.VALUE_STRING, p.nextToken());
    assertEquals("a string", p.getText());
    assertToken(JsonToken.END_OBJECT, p.nextToken());
    assertNull(p.nextToken());
    p.close();
}