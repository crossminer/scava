{
    JavaType type;
    MixInResolver r;
    MapperConfig<?> cfg;
    boolean useAnnotations = cfg.isAnnotationProcessingEnabled();
    AnnotationIntrospector ai =  cfg.getAnnotationIntrospector();
    AnnotatedClass ac = AnnotatedClass.construct(type.getRawClass(), (useAnnotations ? ai : null), r);
    // Do something with ac
}