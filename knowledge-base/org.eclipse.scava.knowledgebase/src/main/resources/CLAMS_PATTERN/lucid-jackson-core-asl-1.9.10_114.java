{
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode src = mapper.createObjectNode();
    ObjectNode dest = mapper.createObjectNode();
    src.put("a string", "a string");
    dest.putAll(src);
}