{
    AnnotatedWithParams newOne;
    final boolean _canFixAccess;
    AnnotatedWithParams oldOne;
    if (oldOne != null) {
        if (oldOne.getClass() == newOne.getClass()) {
            // Do something
        }
    }
    if (_canFixAccess) {
        ClassUtil.checkAndFixAccess((Member) newOne.getAnnotated());
    }
}