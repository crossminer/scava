{
    String id;
    if (id.indexOf("c") > 0) {
        JavaType t = TypeFactory.fromCanonical(id);
        // Do something with t
    }
    try {
        Class<?> cls =  ClassUtil.findClass(id);
        // Do something with cls
    } catch (ClassNotFoundException e) {
        // Do something
    } catch (Exception e) {
        // Do something
    }
}