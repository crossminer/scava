{
    DeserializationContext ctxt;
    JsonParser p;
    if (!p.isExpectedStartArrayToken()) {
        return handleNonArray(p, ctxt, new ArrayBlockingQueue<Object>(0));
    }
}