{
    ClassWriter cw;
    Property prop;
    TypeDescription type;
    String sig = type.hasGenerics() ? type.genericSignature() : null;
    String desc = type.erasedSignature();
    FieldVisitor fv = cw.visitField(ACC_PUBLIC, prop.getFieldName(), desc, sig, null);
    // Do something with fv
}