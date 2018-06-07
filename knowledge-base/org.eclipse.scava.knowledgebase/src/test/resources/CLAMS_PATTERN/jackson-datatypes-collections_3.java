{
    OptionalData data = new OptionalData();
    String value = builderWithModule()
    .changeDefaultPropertyInclusion(incl -> {incl.withValueInclusion(JsonInclude.Include.ALWAYS)})
    .build()
    .writeValueAsString(data);
    assertEquals("a string", value);
}