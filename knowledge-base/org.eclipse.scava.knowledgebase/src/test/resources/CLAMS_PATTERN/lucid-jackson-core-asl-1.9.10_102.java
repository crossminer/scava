{
    JsonWriteContext _writeContext;
    if (!_writeContext.inArray()) {
        _reportError("a string"+_writeContext.getTypeDesc());
    }
    if (_cfgPrettyPrinter != null) {
        // Do something
    } else {
        _writeEndArray();
    }
}