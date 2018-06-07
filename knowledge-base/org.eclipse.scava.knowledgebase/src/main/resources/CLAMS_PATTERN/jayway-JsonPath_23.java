{
    ReadContext invalidJson = JsonPath.parse("a string");
    assertThat(invalidJson, not(withJsonPath("a string", equalTo(0))));
}