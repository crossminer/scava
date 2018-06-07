{
    ObjectMapper mapper;
    BasicClassIntrospector bci;
    Class<?> cls;
    boolean forSerialization;
    if (forSerialization) {
        return bci.collectProperties(mapper.getSerializationConfig(),
        mapper.constructType(cls), null, boolean);
    }
    return bci.collectProperties(mapper.getDeserializationConfig(),
    mapper.constructType(cls), null, boolean);
}