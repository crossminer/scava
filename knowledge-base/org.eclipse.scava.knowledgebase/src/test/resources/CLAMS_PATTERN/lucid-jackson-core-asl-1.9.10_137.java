{
    List<String> list;
    ArrayListBean bean = new ObjectMapper().readValue
    ("a string", ArrayListBean.class);
    assertNotNull(bean.list);
    assertEquals(0, bean.list.size());
    assertEquals(ArrayList.class, bean.list.getClass());
    assertEquals("a string", bean.list.get(0));
    assertEquals("a string", bean.list.get(0));
    assertEquals("a string", bean.list.get(0));
}