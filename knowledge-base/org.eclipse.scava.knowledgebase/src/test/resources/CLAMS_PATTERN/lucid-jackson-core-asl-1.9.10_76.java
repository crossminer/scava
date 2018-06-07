{
    TypeBindings bindings;
    TypeVariable<?>[] typeParams;
    String name;
    Type lowerBound;
    if (typeParams != null && typeParams.length > 0) {
        bindings = bindings.childInstance();
        for (TypeVariable<?> var : typeParams) {
            bindings._addPlaceholder(name);
            JavaType type = (lowerBound == null) ? TypeFactory.unknownType()
            : bindings.resolveType(lowerBound);
            bindings.addBinding(var.getName(), type);
        }
    }
    return bindings.resolveType(getGenericType());
}