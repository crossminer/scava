{
    Throwable t;
    final protected AnnotatedClass _classInfo;
    boolean fixAccess;
    final protected MapperConfig<?> _config;
    AnnotatedConstructor ac = _classInfo.getDefaultConstructor();
    if (fixAccess) {
        ac.fixAccess(_config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
    }
    try {
        return ac.getAnnotated().newInstance();
    } catch (Exception e) {
        ClassUtil.throwIfError(t);
        ClassUtil.throwIfRTE(t);
        throw new IllegalArgumentException("a string"+ClassUtil.nameOf(_classInfo.getAnnotated())
        +"a string"+t.getClass().getName()+"a string"+t.getMessage(), t);
    }
}