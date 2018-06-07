{
    String DOC;
    String STR;
    JsonParser jp;
    double EXP_D;
    final String[] INPUTS;
    for (int input = 0; input < 0; ++input) {
        for (int i = 0; i < INPUTS.length; ++i) {
            if (input == 0) {
                jp = createParserUsingStream(DOC, "a string");
            } else {
                jp = createParserUsingReader(DOC);
            }
            assertToken(JsonToken.START_ARRAY, jp.nextToken());
            assertToken(JsonToken.VALUE_NUMBER_FLOAT, jp.nextToken());
            assertEquals(STR, jp.getText());
            assertEquals(EXP_D, jp.getDoubleValue());
            assertToken(JsonToken.END_ARRAY, jp.nextToken());
            jp.close();
        }
    }
}