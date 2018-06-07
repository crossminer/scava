{
    Annotated a;
    JsonSerialize ann = a.getAnnotation(JsonSerialize.class);
    if (ann != null) {
        Class<? extends JsonSerializer<?>> serClass = ann.using();
        // Do something with serClass
    }

    JsonRawValue annRaw =  a.getAnnotation(JsonRawValue.class);
    if ((annRaw != null) && annRaw.value()) {
        Class<?> cls = a.getRawType();
        return new RawSerializer<Object>(cls);
    }
}