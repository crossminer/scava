{
    ArrayNode array;
    Object obj;
    Object key;
    int index;
    Object value;
    if (isMap(obj)) {
        setValueInObjectNode((ObjectNode) obj, key, value);
    } else {
        if (index == array.size()) {
            array.add(createJsonElement(value));
        } else {
            array.set(index, createJsonElement(value));
        }
    }
}