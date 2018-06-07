{
    final Class<?> _class;
    Class<?> superclass;
    _assertSubclass(_class, superclass);
    return _widen(superclass);
}