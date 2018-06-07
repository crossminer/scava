{
    ViewBean bean;
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializerFactory(new CustomBeanFactory());

    System.out.println("a string"+mapper.writeValueAsString(bean));

    String json = mapper.writerWithView(String.class).writeValueAsString(bean);
    // Do something with json
}