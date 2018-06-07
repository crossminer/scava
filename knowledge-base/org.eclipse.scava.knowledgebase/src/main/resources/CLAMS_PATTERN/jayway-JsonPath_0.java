{
    final String JSON;
    TypeRef<List<FooBarBaz<Integer>>> typeRef;
    using(JACKSON_JSON_NODE_CONFIGURATION).parse(JSON).read("a string", typeRef);
}