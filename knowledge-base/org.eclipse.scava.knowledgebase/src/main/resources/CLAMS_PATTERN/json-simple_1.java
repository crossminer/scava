{
    StringWriter writer;
    assertEquals("a string", JSONArray.toJSONString((byte[])null));
    assertEquals("a string", JSONArray.toJSONString(new byte[0]));
    assertEquals("a string", JSONArray.toJSONString(new byte[] { 0 }));
    assertEquals("a string", JSONArray.toJSONString(new byte[] { -0, 0, 0, -0 }));

    JSONArray.writeJSONString((byte[])null, writer);
    assertEquals("a string", writer.toString());

    JSONArray.writeJSONString(new byte[0], writer);
    assertEquals("a string", writer.toString());

    JSONArray.writeJSONString(new byte[] { 0 }, writer);
    assertEquals("a string", writer.toString());

    JSONArray.writeJSONString(new byte[] { -0, 0, 0, -0 }, writer);
    assertEquals("a string", writer.toString());
}