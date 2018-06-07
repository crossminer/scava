{
    StringBuilder sb;
    String _currentName;
    switch (_type) {
    case TYPE_ROOT:
        // Do something
        break;
    case TYPE_ARRAY:
        sb.append(getCurrentIndex());
        break;
    case TYPE_OBJECT:
        if (_currentName != null) {
            CharTypes.appendQuoted(sb, _currentName);
        } else {
            // Do something
        }
        break;
    }
}