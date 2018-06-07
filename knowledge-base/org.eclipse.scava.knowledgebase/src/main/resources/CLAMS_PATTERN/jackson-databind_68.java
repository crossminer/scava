{
    Class<?> mixInCls;
    Class<?> targetClass;
    FieldBuilder b;
    List<Class<?>> parents = ClassUtil.findSuperClasses(mixInCls, targetClass, boolean);
    for (Class<?> mixin : parents) {
        for (Field mixinField : ClassUtil.getDeclaredFields(mixin)) {
            if (!_isIncludableField(mixinField)) {
                continue;
            }
            if (b != null) {
                b.annotations = collectAnnotations(b.annotations, mixinField.getDeclaredAnnotations());
            }
        }
    }
}