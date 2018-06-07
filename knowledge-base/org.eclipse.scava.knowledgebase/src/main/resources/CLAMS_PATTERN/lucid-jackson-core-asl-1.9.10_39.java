{
    int _features;
    Feature f;
    _features &= ~f.getMask();
    if (f == Feature.WRITE_NUMBERS_AS_STRINGS) {
        // Do something
    } else if (f == Feature.ESCAPE_NON_ASCII) {
        setHighestNonEscapedChar(0);
    }
}