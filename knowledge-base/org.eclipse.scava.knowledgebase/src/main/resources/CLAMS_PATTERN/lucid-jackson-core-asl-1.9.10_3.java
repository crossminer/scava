{
    Float result;
    String[] STRS;
    final static String NAN_STRING;
    Float exp;
    ObjectMapper mapper = new ObjectMapper();

    for (String str : STRS) {
        if (NAN_STRING != str) {
            result = mapper.readValue(new StringReader(str), Float.class);
            assertEquals(exp, result);
        }
        result = mapper.readValue(new StringReader("a string"+str+"a string"), Float.class);
        assertEquals(exp, result);
    }
}