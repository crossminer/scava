{
    FieldNameMatcher _fieldMatcher;
    final boolean _caseInsensitive;
    TokenStreamFactory tsf;
    List<Named> names;
    if (_caseInsensitive) {
        _fieldMatcher = tsf.constructCIFieldNameMatcher(names, boolean);
    } else {
        _fieldMatcher = tsf.constructFieldNameMatcher(names, boolean);
        // Do something with _fieldMatcher
    }
}