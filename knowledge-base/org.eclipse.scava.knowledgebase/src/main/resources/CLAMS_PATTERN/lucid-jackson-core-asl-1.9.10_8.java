{
    final String JSON;
    for (int i = 0; i < 0; ++i) {
        JsonParser jp = (i == 0) ? createParserUsingReader(JSON)
        : createParserUsingStream(JSON, "a string");
        assertToken(JsonToken.START_ARRAY, jp.nextToken());
        try {
            jp.nextToken();
            fail("a string");
        } catch (JsonParseException jpe) {
            verifyException(jpe, "a string");
        }
    }
}