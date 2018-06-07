{
    int id;
    HashMap<String,String> other;
    ObjectMapper m = new ObjectMapper();
    DynaBean b = new DynaBean();
    b.set("a string", "a string");
    assertEquals("a string", m.writeValueAsString(b));

    DynaBean result = m.readValue("a string", DynaBean.class);
    assertEquals(0, result.id);
    assertEquals("a string", result.other.get("a string"));
}