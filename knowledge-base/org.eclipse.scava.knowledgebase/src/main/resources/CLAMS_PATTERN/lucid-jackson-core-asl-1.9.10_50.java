{
    StringWriter out;
    final String EXP;
    ObjectMapper mapper = new ObjectMapper();
    JavaType collectionType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, BaseClass398.class);
    List<TestClass398> typedList = new ArrayList<TestClass398>();
    typedList.add(new TestClass398());

    String json = mapper.writerWithType(collectionType).writeValueAsString(typedList);
    assertEquals(EXP, json);

    JsonFactory f = new JsonFactory();
    mapper.writerWithType(collectionType).writeValue(f.createJsonGenerator(out), typedList);

    assertEquals(EXP, out.toString());
}