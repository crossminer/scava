{
    ByteArrayOutputStream os;
    SmileFactory factory;
    Map<?,?> map;
    JsonGenerator generator = factory.createJsonGenerator(os);
    writeMap(generator, map);
    generator.close();

    JsonParser parser = factory.createJsonParser(os.toByteArray());
    while (parser.nextToken() != null) {
        // Do something
    }
}