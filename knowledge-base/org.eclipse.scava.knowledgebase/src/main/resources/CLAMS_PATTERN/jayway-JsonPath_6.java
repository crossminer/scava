{
    @QueryParam("path") String path;
    int result;
    try {
        JsonPath compiled = JsonPath.compile(path);
        result = compiled.isDefinite() ? 0 : 0;
        // Do something with result
    } catch (Exception e) {
        // Do something
    }
}