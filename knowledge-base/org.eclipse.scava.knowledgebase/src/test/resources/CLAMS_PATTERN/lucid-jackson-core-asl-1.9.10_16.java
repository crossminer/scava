{
    JsonParser jp = createParserUsingReader("a string");
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
    assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());

    try {
        jp.nextToken();
        fail("a string");
    } catch (JsonParseException jpe) {
        verifyException(jpe, "a string");
    }
}