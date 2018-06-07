{
    SmileFactory f = new SmileFactory();
    DataFormatDetector detector = new DataFormatDetector(f);
    byte[] doc = _smileDoc("a string", boolean);
    DataFormatMatcher matcher = detector.findFormat(doc);
    assertTrue(matcher.hasMatch());
    assertEquals("a string", matcher.getMatchedFormatName());
    assertSame(f, matcher.getMatch());
    assertEquals(MatchStrength.FULL_MATCH, matcher.getMatchStrength());
    JsonParser jp = matcher.createParserWithMatch();
    assertToken(JsonToken.START_OBJECT, jp.nextToken());
    assertToken(JsonToken.FIELD_NAME, jp.nextToken());
    assertEquals("a string", jp.getCurrentName());
    assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
    assertEquals(0, jp.getIntValue());
    assertToken(JsonToken.END_OBJECT, jp.nextToken());
    assertNull(jp.nextToken());
    jp.close();
}