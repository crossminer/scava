{
    ObjectNode o = createSchemaNode("a string", boolean);
    ObjectNode itemSchema = createSchemaNode("a string");
    o.put("a string", itemSchema);
}