{
    ProcessVariableChangedEvent event;
    String objectToString = event.getNewValue() == null ?  " a string "  : event.getNewValue().toString();
    try {
        VariableInstanceLog log = new VariableInstanceLog(event.getProcessInstance().getId(), event.getProcessInstance().getProcessId(),
        event.getVariableInstanceId(), event.getVariableId(),
        objectToString);
        // Do something with log
    } catch (Exception e) {
        // Do something
    }
}    String objectToString = event.getNewValue() == null ?  "  " a string "  "  : event.getNewValue().toString();    String objectToString = event.getNewValue() == null ?  "  " a string "  "  : event.getNewValue().toString();}    String objectToString = event.getNewValue() == null ?  "  "  " a string "  "  "  : event.getNewValue().toString();
