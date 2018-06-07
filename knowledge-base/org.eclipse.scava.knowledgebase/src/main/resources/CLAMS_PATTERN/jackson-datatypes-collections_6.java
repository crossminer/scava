{
    OptionalGenericData<String> data = new OptionalGenericData<String>();
    String value = builderWithModule(boolean)
    .changeDefaultPropertyInclusion(incl -> {
        JsonInclude.Value.construct(JsonInclude.Include.NON_ABSENT,
        JsonInclude.Include.NON_ABSENT)
    })
    .build()
    .writeValueAsString(data);
    assertEquals("a string", value);
}