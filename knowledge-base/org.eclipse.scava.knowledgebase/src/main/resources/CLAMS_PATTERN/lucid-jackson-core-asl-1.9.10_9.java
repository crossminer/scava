{
    JsonParser jp;
    switch (jp.getCurrentToken()) {
    case VALUE_STRING:
        if (_baseType.getRawClass().isAssignableFrom(String.class)) {
            return jp.getText();
        }
        break;
    case VALUE_NUMBER_INT:
        if (_baseType.getRawClass().isAssignableFrom(Integer.class)) {
            return jp.getIntValue();
        }
        break;
    case VALUE_NUMBER_FLOAT:
        if (_baseType.getRawClass().isAssignableFrom(Double.class)) {
            return Double.valueOf(jp.getDoubleValue());
        }
        break;
    case VALUE_TRUE:
        // Do something
        break;
    case VALUE_FALSE:
        // Do something
        break;
    }
}