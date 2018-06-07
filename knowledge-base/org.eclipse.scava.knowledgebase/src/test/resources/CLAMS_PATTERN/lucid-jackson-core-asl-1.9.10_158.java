{
    final protected IOContext _ioContext;
    JsonReadContext _parsingContext;
    if (!_parsingContext.inRoot()) {
        _reportInvalidEOF("a string"+_parsingContext.getTypeDesc()+"a string"+_parsingContext.getStartLocation(_ioContext.getSourceReference())+"a string");
    }
}