{
    T obj;
    ClassLoader classLoader;
    return (T) DroolsStreamUtils.streamIn( DroolsStreamUtils.streamOut( obj ),
    classLoader );
}
