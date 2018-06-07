{
    final String DOC;
    JsonFactory f = new JsonFactory();

    assertTrue(f.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE));
    f.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    assertFalse(f.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE));
    MyReader input = new MyReader(DOC);
    JsonParser jp = f.createJsonParser(input);

    assertFalse(input.isClosed());
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
    assertToken(JsonToken.END_ARRAY, jp.nextToken());
    assertNull(jp.nextToken());
    assertFalse(input.isClosed());
    jp.close();
    assertFalse(input.isClosed());

}