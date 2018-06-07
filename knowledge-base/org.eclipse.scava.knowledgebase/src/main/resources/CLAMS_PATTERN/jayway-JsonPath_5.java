{
    Object expectedValue;
    String pathExpr;
    String json;
    Configuration conf;
    Object result = using(conf).parse(json).read(pathExpr);
    assertThat(conf.jsonProvider().unwrap(result)).isEqualTo(expectedValue);
}