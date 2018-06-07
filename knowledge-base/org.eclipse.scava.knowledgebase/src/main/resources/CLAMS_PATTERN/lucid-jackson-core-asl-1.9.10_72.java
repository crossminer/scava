{
    JsonParser jp;
    JsonToken t = jp.getCurrentToken();
    if (t == null) {
        _reportError("a string");
    }
    switch(t) {
    case START_OBJECT:
        writeStartObject();
        break;
    case END_OBJECT:
        writeEndObject();
        break;
    case START_ARRAY:
        writeStartArray();
        break;
    case END_ARRAY:
        writeEndArray();
        break;
    case FIELD_NAME:
        writeFieldName(jp.getCurrentName());
        break;
    case VALUE_STRING:
        if (jp.hasTextCharacters()) {
            writeString(jp.getTextCharacters(), jp.getTextOffset(), jp.getTextLength());
        } else {
            writeString(jp.getText());
        }
        break;
    case VALUE_NUMBER_INT:
        switch (jp.getNumberType()) {
        case INT:
            writeNumber(jp.getIntValue());
            break;
        case BIG_INTEGER:
            writeNumber(jp.getBigIntegerValue());
            break;
        default:
            writeNumber(jp.getLongValue());
        }
        break;
    case VALUE_NUMBER_FLOAT:
        switch (jp.getNumberType()) {
        case BIG_DECIMAL:
            writeNumber(jp.getDecimalValue());
            break;
        case FLOAT:
            writeNumber(jp.getFloatValue());
            break;
        default:
            writeNumber(jp.getDoubleValue());
        }
        break;
    case VALUE_TRUE:
        writeBoolean(boolean);
        break;
    case VALUE_FALSE:
        writeBoolean(boolean);
        break;
    case VALUE_NULL:
        writeNull();
        break;
    case VALUE_EMBEDDED_OBJECT:
        writeObject(jp.getEmbeddedObject());
        break;
    default:
        _cantHappen();
    }
}