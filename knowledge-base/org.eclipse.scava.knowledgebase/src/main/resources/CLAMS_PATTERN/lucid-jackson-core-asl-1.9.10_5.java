{
    int _outputEnd;
    int _outputTail;
    _verifyValueWrite("a string");
    if (_cfgPrettyPrinter != null) {
        // Do something
    } else {
        if (_outputTail >= _outputEnd) {
            _flushBuffer();
        }
    }
}