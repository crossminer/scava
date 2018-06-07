{
    final ImmutableTable.Builder<Integer, ComplexKey, String> ckBuilder;
    final String ckJson;
    ckBuilder.put(Integer.valueOf(0), new ComplexKey("a string", "a string"), "a string");
    ckBuilder.put(Integer.valueOf(0), new ComplexKey("a string", "a string"), "a string");
    assertEquals("a string", ckJson);
}