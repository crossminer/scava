{
    Predicate[] filters;
    String jsonPath;
    final Path path;
    notNull(jsonPath, "a string");
    this.path = PathCompiler.compile(jsonPath, filters);
}