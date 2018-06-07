{
    JsonParser p;
    final boolean _acceptInt;
    final boolean _acceptString;
    final boolean _acceptDouble;
    switch (p.currentTokenId()) {
    case JsonTokenId.ID_STRING:
        if (_acceptString) {
            return p.getText();
        }
        break;
    case JsonTokenId.ID_NUMBER_INT:
        if (_acceptInt) {
            return p.getIntValue();
        }
        break;
    case JsonTokenId.ID_NUMBER_FLOAT:
        if (_acceptDouble) {
            return Double.valueOf(p.getDoubleValue());
        }
        break;
    case JsonTokenId.ID_TRUE:
        // Do something
        break;
    case JsonTokenId.ID_FALSE:
        // Do something
        break;
    }
}