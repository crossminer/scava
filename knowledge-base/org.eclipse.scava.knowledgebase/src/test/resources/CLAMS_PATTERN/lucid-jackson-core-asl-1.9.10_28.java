{
    double value;
    float _v;
    ObjectMapper mapper = new ObjectMapper();
    DoubleBean result = mapper.readValue(new StringReader("a string"+value+"a string"), DoubleBean.class);
    assertEquals(value, result._v);
    result = mapper.readValue(new StringReader("a string"), DoubleBean.class);
    assertNotNull(result);
    assertEquals(0, result._v);

    double[] array = mapper.readValue(new StringReader("a string"), double[].class);
    assertNotNull(array);
    assertEquals(0, array.length);
    assertEquals(0, array[0]);
}