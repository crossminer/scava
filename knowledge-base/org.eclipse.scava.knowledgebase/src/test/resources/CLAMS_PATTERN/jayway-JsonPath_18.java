{
    Configuration configuration;
    final Path path;
    Object jsonObject;
    Object newVal;
    notNull(jsonObject, "a string");
    notNull(configuration, "a string");
    EvaluationContext evaluationContext = path.evaluate(jsonObject, jsonObject, configuration, boolean);
    for (PathRef updateOperation : evaluationContext.updateOperations()) {
        updateOperation.set(newVal, configuration);
    }
    return resultByConfiguration(jsonObject, configuration, evaluationContext);
}