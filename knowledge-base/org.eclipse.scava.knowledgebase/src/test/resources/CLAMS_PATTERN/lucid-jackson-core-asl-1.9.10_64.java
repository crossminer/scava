{
    Class<?> type;
    HashMap<ClassKey,JsonSerializer<?>> _interfaceMappings;
    HashMap<ClassKey,JsonSerializer<?>> _transitiveClassMappings;
    JsonSerializer<?> ser;
    ClassKey key = new ClassKey(type);

    if (_transitiveClassMappings != null) {
        for (Class<?> curr = type; (curr != null); curr = curr.getSuperclass()) {
            key.reset(curr);
        }
    }

    if (_interfaceMappings != null) {
        key.reset(type);
        for (Class<?> curr = type; (curr != null); curr = curr.getSuperclass()) {
            ser = _findInterfaceMapping(curr, key);
            // Do something with ser
        }
    }
}