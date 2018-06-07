{
    AbstractTypeMaterializer mat = new AbstractTypeMaterializer();
    DeserializationConfig config = new ObjectMapper().getDeserializationConfig();
    try {
        mat.materializeClass(config, InvalidBean.class);
        fail("a string");
    } catch (IllegalArgumentException e) {
        verifyException(e, "a string");
    }
}