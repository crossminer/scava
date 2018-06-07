{
    AnnotatedMethod am;
    JsonValue ann = am.getAnnotation(JsonValue.class);
    return (ann != null && ann.value());
}