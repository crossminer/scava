{
    StringWriter writer;
    assertEquals("a string", JSONValue.toJSONString((short[])null));
    assertEquals("a string", JSONValue.toJSONString(new short[0]));
    assertEquals("a string", JSONValue.toJSONString(new short[] { 0 }));
    assertEquals("a string", JSONValue.toJSONString(new short[] { -0, 0, 0, -0 }));

    JSONValue.writeJSONString((short[])null, writer);
    assertEquals("a string", writer.toString());

    JSONValue.writeJSONString(new short[0], writer);
    assertEquals("a string", writer.toString());

    JSONValue.writeJSONString(new short[] { 0 }, writer);
    assertEquals("a string", writer.toString());

    JSONValue.writeJSONString(new short[] { -0, 0, 0, -0 }, writer);
    assertEquals("a string", writer.toString());
}