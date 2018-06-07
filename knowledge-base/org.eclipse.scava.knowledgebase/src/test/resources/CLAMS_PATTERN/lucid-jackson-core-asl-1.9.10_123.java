{
    JsonGenerator jg;
    Indenter _objectIndenter;
    jg.writeRaw("c");
    if (!_objectIndenter.isInline()) {
        // Do something
    }
}