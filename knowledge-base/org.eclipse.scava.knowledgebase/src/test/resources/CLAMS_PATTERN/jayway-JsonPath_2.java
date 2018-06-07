{
    ArraySliceOperation operation;
    final Logger logger;
    EvaluationContextImpl ctx;
    String currentPath;
    Object model;
    int length = ctx.jsonProvider().length(model);
    int from = operation.from();
    from = Math.max(0, from);

    logger.debug("a string", length, from, length - 0, toString());

    for (int i = from; i < length; i++) {
        handleArrayIndex(i, currentPath, model, ctx);
    }
}