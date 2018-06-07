{
    JsonNode value;
    int index;
    if (value == null) {
        value = nullNode();
    }
    return _set(index, value);
}