{
    BoundType lowerBoundType;
    Comparable<?> lowerEndpoint;
    DeserializationContext context;
    JsonToken t;
    String fieldName;
    JsonParser p;
    BoundType upperBoundType;
    Comparable<?> upperEndpoint;
    for (; t != JsonToken.END_OBJECT; t = p.nextToken()) {
        expect(context, JsonToken.FIELD_NAME, t);
        try {
            if (fieldName.equals("a string")) {
                lowerEndpoint = deserializeEndpoint(context, p);
            } else if (fieldName.equals("a string")) {
                upperEndpoint = deserializeEndpoint(context, p);
            } else if (fieldName.equals("a string")) {
                lowerBoundType = deserializeBoundType(context, p);
            } else if (fieldName.equals("a string")) {
                upperBoundType = deserializeBoundType(context, p);
            } else {
                // Do something
            }
        } catch (IllegalStateException e) {
            // Do something
        }
    }
    try {
        if ((lowerEndpoint != null) && (upperEndpoint != null)) {
            return RangeFactory.range(lowerEndpoint, lowerBoundType, upperEndpoint, upperBoundType);
        }
        if (lowerEndpoint != null) {
            return RangeFactory.downTo(lowerEndpoint, lowerBoundType);
        }
        if (upperEndpoint != null) {
            return RangeFactory.upTo(upperEndpoint, upperBoundType);
        }
        return RangeFactory.all();
    } catch (IllegalStateException e) {
        // Do something
    }
}