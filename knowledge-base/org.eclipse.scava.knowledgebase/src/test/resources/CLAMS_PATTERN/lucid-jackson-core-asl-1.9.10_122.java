{
    SimpleModule module = new SimpleModule("a string", Version.unknownVersion());
    module.setMixInAnnotation(MixableBean.class, MixInForOrder.class);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);
    Map<String,Object> props = this.writeAndMap(mapper, new MixableBean());
    assertEquals(0, props.size());
    assertEquals(Integer.valueOf(0), props.get("a string"));
    assertEquals(Integer.valueOf(0), props.get("a string"));
    assertEquals(Integer.valueOf(0), props.get("a string"));
}