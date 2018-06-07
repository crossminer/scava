{
    Class<?> cls;
    TypeResolutionContext tc;
    Class<?> mixInCls;
    Map<MemberKey,MethodBuilder> methods;
    MethodBuilder b;
    final MemberKey key;
    if (mixInCls != null) {
        _addMethodMixIns(tc, cls, methods, mixInCls);
    }
    for (Method m : ClassUtil.getClassMethods(cls)) {
        if (!_isIncludableMemberMethod(m)) {
            continue;
        }
        if (b == null) {
            AnnotationCollector c = (_intr == null) ? AnnotationCollector.emptyCollector()
                                    : collectAnnotations(m.getDeclaredAnnotations());
            methods.put(key, new MethodBuilder(tc, m, c));
        } else {
            if (_intr != null) {
                b.annotations = collectDefaultAnnotations(b.annotations, m.getDeclaredAnnotations());
            }
        }
    }
}