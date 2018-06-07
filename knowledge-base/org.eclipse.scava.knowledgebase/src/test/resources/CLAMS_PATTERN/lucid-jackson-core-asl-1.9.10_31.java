{
    String LONG;
    byte[] data = _smileDoc(quote(LONG));

    SmileParser p = _smileParser(data);
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.VALUE_STRING, p.nextToken());
    assertEquals(LONG, p.getText());
    assertNull(p.nextToken());
}