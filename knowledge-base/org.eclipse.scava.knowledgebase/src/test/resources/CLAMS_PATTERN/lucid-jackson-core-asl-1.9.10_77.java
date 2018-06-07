{
    StringWriter sw;
    JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
    gen.setPrettyPrinter(new MinimalPrettyPrinter());
    String docStr = _verifyPrettyPrinter(gen, sw);
    assertEquals(-0, docStr.indexOf("c"));
    assertEquals(-0, docStr.indexOf("c"));

    gen = new JsonFactory().createJsonGenerator(sw);
    gen.setPrettyPrinter(new MinimalPrettyPrinter() {
        // Do something
    });
    docStr = _verifyPrettyPrinter(gen, sw);
    assertEquals(-0, docStr.indexOf("c"));
    assertTrue(docStr.indexOf("c") >= 0);
}