{
    Class<?> cls;
    String str;
    Class<?> valueClass;
    Object value;
    Class<?> staticType;
    if (str.startsWith("a string")) {
        if (value instanceof EnumSet<?>) {
            Class<?> enumClass = ClassUtil.findEnumType((EnumSet<?>) value);
            str = TypeFactory.defaultInstance().constructCollectionType(EnumSet.class, enumClass).toCanonical();
        } else if (value instanceof EnumMap<?,?>) {
            Class<?> enumClass = ClassUtil.findEnumType((EnumMap<?,?>) value);
            str = TypeFactory.defaultInstance().constructMapType(EnumMap.class, enumClass, valueClass).toCanonical();
        } else {
            // Do something
        }
    } else if (str.indexOf("c") >= 0) {
        Class<?> outer = ClassUtil.getOuterClass(cls);
        if (outer != null) {
            if (ClassUtil.getOuterClass(staticType) == null) {
                // Do something
            }
        }
    }
}