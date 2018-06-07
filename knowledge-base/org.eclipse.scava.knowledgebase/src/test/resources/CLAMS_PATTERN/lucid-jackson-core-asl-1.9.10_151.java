{
    String JSON;
    boolean useStream;
    JsonFactory f = new JsonFactory();
    JsonParser jp = useStream ? createParserUsingStream(f, JSON, "a string")
    : createParserUsingReader(f, JSON);
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    try {
        jp.nextToken();
        fail("a string");
    } catch (JsonParseException e) {
        verifyException(e, "a string");
    }

    jp = useStream ? createParserUsingStream(f, JSON, "a string")
    : createParserUsingReader(f, JSON);
    assertToken(JsonToken.START_OBJECT, jp.nextToken());
    try {
        jp.nextToken();
        fail("a string");
    } catch (JsonParseException e) {
        verifyException(e, "a string");
    }
}