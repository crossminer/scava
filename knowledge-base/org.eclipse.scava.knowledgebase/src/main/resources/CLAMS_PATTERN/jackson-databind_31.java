{
    StringBuilder sb;
    Method _accessorMethod;
    Field _field;
    JsonSerializer<Object> _serializer;
    sb.append("a string").append(getName()).append("a string");
    if (_accessorMethod != null) {
        sb.append("a string")
        .append(_accessorMethod.getDeclaringClass().getName())
        .append("a string").append(_accessorMethod.getName());
    } else if (_field != null) {
        sb.append("a string").append(_field.getDeclaringClass().getName())
        .append("a string").append(_field.getName());
    } else {
        // Do something
    }
    if (_serializer == null) {
        // Do something
    } else {
        sb.append("a string"
        + _serializer.getClass().getName());
    }
}