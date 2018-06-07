{
    HashMap<String, Integer> data;
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer();
    assertEquals("a string", writer.writeValueAsString(data));

    writer = writer.withDefaultPrettyPrinter();
    assertEquals("a string", writer.writeValueAsString(data));

    writer = writer.withPrettyPrinter(null);
    assertEquals("a string", writer.writeValueAsString(data));
}