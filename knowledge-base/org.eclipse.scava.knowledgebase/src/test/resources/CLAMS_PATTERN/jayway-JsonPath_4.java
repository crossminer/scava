{
    final ReadContext JSON;
    assertThat(JSON, withoutJsonPath(compile("a string")));
    assertThat(JSON, withoutJsonPath("a string"));
}