{
    int _outputEnd;
    int _outputTail;
    if (!_writeContext.inObject()) {
        _reportError("a string"+_writeContext.getTypeDesc());
    }
    if (_cfgPrettyPrinter != null) {
        // Do something
    } else {
        if (_outputTail >= _outputEnd) {
            _flushBuffer();
        }
    }
}