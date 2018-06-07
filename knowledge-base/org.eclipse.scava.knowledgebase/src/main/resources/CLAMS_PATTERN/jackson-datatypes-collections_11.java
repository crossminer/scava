{
    JavaType t;
    assertNotNull(t);
    assertEquals(Optional.class, t.getRawClass());
    assertTrue(t.isReferenceType());
}