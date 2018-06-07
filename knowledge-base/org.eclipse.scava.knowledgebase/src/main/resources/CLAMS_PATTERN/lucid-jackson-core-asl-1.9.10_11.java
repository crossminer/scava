{
    float value;
    float _v;
    ObjectMapper mapper = new ObjectMapper();
    FloatBean result = mapper.readValue(new StringReader("a string"+value+"a string"), FloatBean.class);
    assertEquals(value, result._v);

    float[] array = mapper.readValue(new StringReader("a string"), float[].class);
    assertNotNull(array);
    assertEquals(0, array.length);
    assertEquals(Float.POSITIVE_INFINITY, array[0]);
}