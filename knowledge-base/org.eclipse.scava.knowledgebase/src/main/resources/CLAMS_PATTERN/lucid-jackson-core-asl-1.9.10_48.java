{
    StringWriter sw;
    final int COUNT;
    ArrayList<CtorValueBean> beans = new ArrayList<CtorValueBean>();
    for (int i = 0; i < COUNT; ++i) {
        beans.add(new CtorValueBean(i));
    }
    BeanWithList bean = new BeanWithList(beans);

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(sw, bean);

    BeanWithList result = new ObjectMapper().readValue(sw.toString(), BeanWithList.class);
    assertEquals(bean, result);
}