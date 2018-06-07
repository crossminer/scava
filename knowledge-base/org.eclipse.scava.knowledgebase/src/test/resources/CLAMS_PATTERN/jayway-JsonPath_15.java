{
    Object array;
    Object value;
    int index;
    if( !isArray(array) ) {
        // Do something
    }

    try {
        ((org.codehaus.jettison.json.JSONArray)array).put(index, jettisonWrap(value));
    } catch( org.codehaus.jettison.json.JSONException jsonException ) {
        // Do something
    }
}