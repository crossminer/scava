{
    String id;
    AnnotatedMember m;
    AnnotatedMethod am;
    JacksonInject ann = m.getAnnotation(JacksonInject.class);
    if (id.length() == 0) {
        if (!(m instanceof AnnotatedMethod)) {
            return m.getRawType().getName();
        }
        if (am.getParameterCount() == 0) {
            return m.getRawType().getName();
        }
        return am.getParameterClass(0).getName();
    }
    // Do something with ann
}