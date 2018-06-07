{
    List<String> strings;
    byte[] input;
    JsonFactory f;
    JsonParser jp = f.createJsonParser(input);
    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    for (int i = 0, len = strings.size(); i < len; ++i) {
        assertToken(JsonToken.VALUE_STRING, jp.nextToken());
        assertEquals(strings.get(i), jp.getText());
    }
    assertToken(JsonToken.END_ARRAY, jp.nextToken());
    jp.close();
}