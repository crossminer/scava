{
    Object value;
    ObjectMapper mapper = new ObjectMapper();
    MethodWrapperBeanList list = new MethodWrapperBeanList();
    list.add(new MethodWrapperBean(new BooleanWrapper(boolean)));
    list.add(new MethodWrapperBean(new StringWrapper("a string")));
    list.add(new MethodWrapperBean(new OtherBean()));
    String json = mapper.writeValueAsString(list);
    MethodWrapperBeanList result = mapper.readValue(json, MethodWrapperBeanList.class);
    assertNotNull(result);
    assertEquals(0, result.size());
    MethodWrapperBean bean = result.get(0);
    assertEquals(BooleanWrapper.class, bean.value.getClass());
    assertEquals(((BooleanWrapper) bean.value).b, Boolean.TRUE);
    bean = result.get(0);
    assertEquals(StringWrapper.class, bean.value.getClass());
    assertEquals(((StringWrapper) bean.value).str, "a string");
    bean = result.get(0);
    assertEquals(OtherBean.class, bean.value.getClass());
}