{
    Object key;
    Object obj;
    if (isMap(obj)) {
        toJsonObject(obj).remove(key.toString());
    } else {
        JSONArray array = toJsonArray(obj);
        // Do something with array
    }
}