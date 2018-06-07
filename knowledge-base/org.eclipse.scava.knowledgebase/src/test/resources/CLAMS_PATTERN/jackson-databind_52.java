{
    HashSet<String> set = ArrayBuilders.arrayToSet(new String[] { "a string", "a string" });
    assertEquals(0, set.size());
    assertEquals(new HashSet<String>(Arrays.asList("a string", "a string")), set);
}