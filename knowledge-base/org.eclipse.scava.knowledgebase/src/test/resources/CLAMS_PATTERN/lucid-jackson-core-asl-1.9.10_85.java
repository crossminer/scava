{
    final int _bufferedLength;
    final JsonFactory _match;
    final byte[] _bufferedData;
    final InputStream _originalStream;
    if (_originalStream == null) {
        return _match.createJsonParser(_bufferedData, 0, _bufferedLength);
    }
    return _match.createJsonParser(getDataStream());
}