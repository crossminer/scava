{
    final protected BytesToNameCanonicalizer _parent;
    if (_parent != null && maybeDirty()) {
        _parent.mergeChild(new TableInfo(this));
    }
}