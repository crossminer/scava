{
    String json;
    MyBeanValue value;
    MyBeanDefaultValue defaultValue;
    BigDecimal decimal;
    ObjectMapper mapper = new ObjectMapper();
    try {
        MyBeanHolder result = mapper.readValue(json, MyBeanHolder.class);
        fail("a string" + result.defaultValue.value.decimal.toString());
    } catch (JsonParseException e) {
        verifyException(e, "a string");
    }
}