{
    Class<?> cls;
    Class<?> mixInCls;
    AnnotatedMethodMap mixIns;
    AnnotatedMethodMap methods;
    MethodFilter methodFilter;
    AnnotatedMethod old;
    if (mixInCls != null) {
        _addMethodMixIns(cls, methodFilter, methods, mixInCls, mixIns);
    }

    for (Method m : cls.getDeclaredMethods()) {
        if (!_isIncludableMethod(m, methodFilter)) {
            continue;
        }
        if (old == null) {
            AnnotatedMethod newM = _constructMethod(m);
            if (old != null) {
                _addMixOvers(old.getAnnotated(), newM, boolean);
            }
        } else {
            _addMixUnders(m, old);
        }
    }
}