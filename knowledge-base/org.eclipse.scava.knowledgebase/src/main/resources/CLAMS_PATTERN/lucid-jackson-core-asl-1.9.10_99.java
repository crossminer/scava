{
    Throwable t;
    boolean fixAccess;
    final protected AnnotatedClass _classInfo;
    AnnotatedConstructor ac = _classInfo.getDefaultConstructor();
    if (fixAccess) {
        ac.fixAccess();
    }
    try {
        return ac.getAnnotated().newInstance();
    } catch (Exception e) {
        throw new IllegalArgumentException("a string"+_classInfo.getAnnotated().getName()+"a string"+t.getClass().getName()+"a string"+t.getMessage(), t);
    }
}