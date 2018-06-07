{
    String path;
    Predicate... filters;
    Object newValue;
    return set(compile(path, filters), newValue);
}