{
    byte[] data = _smileDoc("a string");
    SmileParser p = _smileParser(data);
    assertNull(p.getCurrentToken());
    assertToken(JsonToken.START_ARRAY, p.nextToken());
    assertToken(JsonToken.VALUE_TRUE, p.nextToken());
    assertToken(JsonToken.VALUE_NULL, p.nextToken());
    assertToken(JsonToken.VALUE_FALSE, p.nextToken());
    assertToken(JsonToken.END_ARRAY, p.nextToken());
    assertNull(p.nextToken());
    p.close();
}