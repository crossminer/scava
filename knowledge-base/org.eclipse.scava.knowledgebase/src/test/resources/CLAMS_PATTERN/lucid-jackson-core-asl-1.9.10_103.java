{
    StringBuilder sb;
    final JavaType _keyType;
    final JavaType _valueType;
    _classSignature(_class, sb, boolean);
    _keyType.getGenericSignature(sb);
    _valueType.getGenericSignature(sb);
}