{
    ObjectMapper _mapper;
    Annotations[] _defaultAnnotationsToUse;
    if (_mapper == null) {
        _mapper = new ObjectMapper();
        _setAnnotations(_mapper, _defaultAnnotationsToUse);
    }
}