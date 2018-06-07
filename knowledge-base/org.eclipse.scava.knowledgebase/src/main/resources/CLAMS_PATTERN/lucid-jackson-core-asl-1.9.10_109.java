{
    final protected MapperConfig<?> _config;
    TypeBindings _bindings;
    if (_bindings == null) {
        _bindings = new TypeBindings(_config.getTypeFactory(), _type);
        // Do something with _bindings
    }
}