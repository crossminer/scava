{
    ObjectMapper mapper = new ObjectMapper();
    try {
        mapper.writeValueAsString(new CustomBean("a string", 0));
        fail("a string");
    } catch (IOException e) {
        verifyException(e, "a string");
    }

    try {
        mapper.readValue("a string", CustomBean.class);
        fail("a string");
    } catch (IOException e) {
        verifyException(e, "a string");
    }
}