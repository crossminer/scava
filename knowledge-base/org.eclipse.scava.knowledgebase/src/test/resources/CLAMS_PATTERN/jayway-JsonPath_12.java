{
    Object model;
    EvaluationContextImpl ctx;
    String currentPath;
    PathRef parent;
    PathToken pt;
    Predicate predicate;
    if (ctx.jsonProvider().isMap(model)) {
        walkObject(pt, currentPath, parent, model, ctx, predicate);
    } else if (ctx.jsonProvider().isArray(model)) {
        walkArray(pt, currentPath, parent, model, ctx, predicate);
    }
}