{
    Object obj;
    List<String> keys = new ArrayList<String>(length(obj));

    if( obj instanceof org.codehaus.jettison.json.JSONArray ) {
        for (int i = 0; i < length(obj); i++) {
            keys.add(String.valueOf(i));
        }
    }
    if( obj instanceof org.codehaus.jettison.json.JSONObject ) {
        Iterator<?> keysIt = ((org.codehaus.jettison.json.JSONObject)obj).keys();
        while (keysIt.hasNext()) {
            keys.add(String.valueOf(keysIt.next()));
        }
    }
}