{
    int exp;
    int[] stuff;
    final ObjectReader BIGGIE_READER;
    Biggie value = BIGGIE_READER.readValue(aposToQuotes(
        "a string"
    ));
    for (int i = 0; i < stuff.length; ++i) {
        assertEquals("a string"+i, exp, stuff[i]);
    }
    // Do something with value

}