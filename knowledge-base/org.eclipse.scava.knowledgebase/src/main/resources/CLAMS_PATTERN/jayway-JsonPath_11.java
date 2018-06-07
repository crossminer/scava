{
    Object newValue;
    Object array;
    int index;
    if (!isArray(array)) {
        // Do something
    } else {
        ArrayNode arrayNode = toJsonArray(array);
        if (index == arrayNode.size()) {
            arrayNode.add(createJsonElement(newValue));
        } else {
            arrayNode.set(index, createJsonElement(newValue));
        }
    }
}