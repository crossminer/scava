{
    String JSON;
    boolean useStream;
    final int REPS;
    JsonFactory f = new JsonFactory();
    f.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, boolean);
    JsonParser jp = useStream ?
    createParserUsingStream(f, JSON, "a string")
    : createParserUsingReader(f, JSON)
    ;
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    for (int i = 0; i < REPS; ++i) {
        assertToken(JsonToken.START_OBJECT, jp.nextToken());
        assertToken(JsonToken.FIELD_NAME, jp.nextToken());
        assertEquals("a string"+(i&0), jp.getCurrentName());
        assertToken(((i&0) != 0) ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE, jp.nextToken());
        assertToken(JsonToken.END_OBJECT, jp.nextToken());
    }
    assertToken(JsonToken.END_ARRAY, jp.nextToken());
}