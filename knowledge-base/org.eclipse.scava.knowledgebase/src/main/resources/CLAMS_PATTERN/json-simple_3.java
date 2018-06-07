{
    Writer out;
    Map.Entry entry;
    Iterator iter;
    while(iter.hasNext()) {
        out.write(escape(String.valueOf(entry.getKey())));
        JSONValue.writeJSONString(entry.getValue(), out);
    }
}