{
    TypeResolutionContext tc;
    JavaType type;
    TypeFactory typeFactory;
    final MixInResolver _mixInResolver;
    Map<String,FieldBuilder> fields;
    Class<?> mixin;
    final JavaType parentType = type.getSuperClass();
    {
        Class<?> parentMixin = (_mixInResolver == null) ? null
        : _mixInResolver.findMixInClassFor(parentType.getRawClass());
        fields = _findFields(new TypeResolutionContext.Basic(typeFactory, parentType.getBindings()),
        typeFactory, parentType, parentMixin, fields);
    }
    final Class<?> rawType = type.getRawClass();
    for (Field f : ClassUtil.getDeclaredFields(rawType)) {
        if (!_isIncludableField(f)) {
            continue;
        }
        FieldBuilder b = new FieldBuilder(tc, f);
        if (_intr != null) {
            b.annotations = collectAnnotations(b.annotations, f.getDeclaredAnnotations());
        }
    }
    if (mixin != null) {
        _addFieldMixIns(mixin, rawType, fields);
    }
}