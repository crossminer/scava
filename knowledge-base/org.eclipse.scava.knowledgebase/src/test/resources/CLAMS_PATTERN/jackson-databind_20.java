{
    final int size;
    int index;
    final List<JsonNode> _children;
    JsonNode value;
    if (value == null) {
        value = nullNode();
        // Do something with value
    }
    if (index < 0 || index >= _children.size()) {
        throw new IndexOutOfBoundsException("a string"+ index +"a string"+size());
    }
}