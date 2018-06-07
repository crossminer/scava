{
    TypeResolutionContext tc;
    Class<?> targetClass;
    Class<?> mixInCls;
    Annotation[] anns;
    Map<MemberKey,MethodBuilder> methods;
    MethodBuilder b;
    final MemberKey key;
    for (Class<?> mixin : ClassUtil.findRawSuperTypes(mixInCls, targetClass, boolean)) {
        for (Method m : ClassUtil.getDeclaredMethods(mixin)) {
            if (!_isIncludableMemberMethod(m)) {
                continue;
            }
            if (b == null) {
                methods.put(key, new MethodBuilder(tc, null, collectAnnotations(anns)));
            } else {
                b.annotations = collectDefaultAnnotations(b.annotations, anns);
            }
        }
    }
}