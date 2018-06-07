{
    IOContext ctxt;
    char[] _outputBuffer;
    _outputBuffer = ctxt.allocConcatBuffer();
    if (isEnabled(Feature.ESCAPE_NON_ASCII)) {
        setHighestNonEscapedChar(0);
    }
    // Do something with _outputBuffer

}