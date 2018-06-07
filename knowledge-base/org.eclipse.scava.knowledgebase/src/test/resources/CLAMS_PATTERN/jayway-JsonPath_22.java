{
    String jsonPath;
    return describedAs("a string",
    isJson(withJsonPath(jsonPath)),
    jsonPath);
}