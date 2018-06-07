{
    Predicate... filters;
    String jsonPath;
    notEmpty(jsonPath, "a string");

    return new JsonPath(jsonPath, filters);
}