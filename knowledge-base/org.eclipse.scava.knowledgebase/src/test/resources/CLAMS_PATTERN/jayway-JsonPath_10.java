{
    Object obj;
    String key;
    Object value = ((org.codehaus.jettison.json.JSONObject)obj).opt(key);
    return jettisonUnwrap(value);
}