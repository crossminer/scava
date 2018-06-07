{
    DeserializationContext ctxt;
    BeanDescription beanDesc;
    AnnotatedParameter param;
    ctxt.reportBadDefinition(beanDesc.getType(), String.format(
        "a string",
        param.getIndex()));
}