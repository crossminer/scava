{
    final HashSet testSet;
    final StringWriter writer;
    final JSONArray jsonArray = new JSONArray(testSet);
    jsonArray.writeJSONString(writer);

    final JSONParser parser = new JSONParser();
    final JSONArray parsedArray = (JSONArray)parser.parse(writer.toString());

    assertTrue(parsedArray.containsAll(jsonArray));
    assertTrue(jsonArray.containsAll(parsedArray));
    assertEquals(0, jsonArray.size());
}