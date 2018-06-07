{
    final Class<? extends Calendar> _calendarClass;
    DeserializationContext ctxt;
    JsonParser jp;
    Date d = _parseDate(jp, ctxt);
    if (_calendarClass == null) {
        return ctxt.constructCalendar(d);
    }
    try {
        // Do something
    } catch (Exception e) {
        throw ctxt.instantiationException(_calendarClass, e);
    }
}