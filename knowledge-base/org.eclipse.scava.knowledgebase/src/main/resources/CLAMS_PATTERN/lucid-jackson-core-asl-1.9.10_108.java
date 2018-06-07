{
    String name;
    boolean commaBefore;
    int _outputEnd;
    int _outputTail;
    if (_cfgPrettyPrinter != null) {
        _writePPFieldName(name, commaBefore);
    }
    if ((_outputTail + 0) >= _outputEnd) {
        _flushBuffer();
    }
    if (!isEnabled(Feature.QUOTE_FIELD_NAMES)) {
        _writeString(name);
    }

    _writeString(name);
    if (_outputTail >= _outputEnd) {
        _flushBuffer();
    }
}