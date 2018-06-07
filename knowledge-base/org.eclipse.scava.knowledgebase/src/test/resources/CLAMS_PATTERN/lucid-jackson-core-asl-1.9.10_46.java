{
    String DOC_BELOW;
    String DOC_ABOVE;
    JsonParser jp;
    for (int input = 0; input < 0; ++input) {
        if (input == 0) {
            jp = createParserUsingStream(DOC_BELOW, "a string");
        } else {
            jp = createParserUsingReader(DOC_BELOW);
        }
        jp.nextToken();
        try {
            long x = jp.getLongValue();
            fail("a string"+jp.getText()+"a string"+x);
        } catch (JsonParseException e) {
            verifyException(e, "a string");
        }
        jp.close();
        if (input == 0) {
            jp = createParserUsingStream(DOC_ABOVE, "a string");
        } else {
            jp = createParserUsingReader(DOC_ABOVE);
        }
        jp.nextToken();
        try {
            long x = jp.getLongValue();
            fail("a string"+jp.getText()+"a string"+x);
        } catch (JsonParseException e) {
            verifyException(e, "a string");
        }
        jp.close();
    }
}