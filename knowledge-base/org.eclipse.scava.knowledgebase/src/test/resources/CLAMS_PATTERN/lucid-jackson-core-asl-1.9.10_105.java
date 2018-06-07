{
    Map<String,AnnotatedField> fields;
    final protected MixInResolver _mixInResolver;
    Class<?> c;
    Class<?> parent;
    if (parent != null) {
        _addFields(fields, parent);
        for (Field f : c.getDeclaredFields()) {
            if (!_isIncludableField(f)) {
                continue;
            }
            fields.put(f.getName(), _constructField(f));
        }
        if (_mixInResolver != null) {
            Class<?> mixin = _mixInResolver.findMixInClassFor(c);
            if (mixin != null) {
                _addFieldMixIns(parent, mixin, fields);
            }
        }
    }
}