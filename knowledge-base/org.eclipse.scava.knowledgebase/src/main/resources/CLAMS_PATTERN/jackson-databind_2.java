{
    Object o;
    final transient Field _field;
    return ClassUtil.hasClass(o, getClass())
    && (((AnnotatedField) o)._field == _field);
}