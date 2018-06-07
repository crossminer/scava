{
    boolean addEndMarker;
    boolean addHeader;
    SmileParser jp;
    SmileFactory f = smileFactory(boolean, addHeader, addEndMarker);
    assertToken(JsonToken.START_OBJECT, jp.nextToken());
    assertToken(JsonToken.END_OBJECT, jp.nextToken());

    if (addHeader || addEndMarker) {
        assertNull(jp.nextToken());
    }

    assertToken(JsonToken.START_ARRAY, jp.nextToken());
    assertToken(JsonToken.END_ARRAY, jp.nextToken());

    assertNull(jp.nextToken());
    assertNull(jp.nextToken());
    // Do something with f

}