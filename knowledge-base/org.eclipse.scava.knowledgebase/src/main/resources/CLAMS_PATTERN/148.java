{
    WorkItem wi;
    WorkItemManager wim;
    Map<String, Object> parameters;
    Call call = (Call) wi.getParameter( " a string " );
    String procedureName = (String) wi.getParameter( " a string " );
    Emergency emergency = (Emergency) wi.getParameter( " a string " );
    try {
        MessageFactory.sendMessage(new AsyncProcedureStartMessage(emergency.getId(), wi.getId(), procedureName, parameters));
    } catch (HornetQException ex) {
        // Do something
    }
    wim.completeWorkItem(wi.getId(), null);
    // Do something with call

}    Call call = (Call) wi.getParameter( "  " a string "  " );    String procedureName = (String) wi.getParameter( "  " a string "  " );    Emergency emergency = (Emergency) wi.getParameter( "  " a string "  " );    Call call = (Call) wi.getParameter( "  " a string "  " );    String procedureName = (String) wi.getParameter( "  " a string "  " );    Emergency emergency = (Emergency) wi.getParameter( "  " a string "  " );}    Call call = (Call) wi.getParameter( "  "  " a string "  "  " );    String procedureName = (String) wi.getParameter( "  "  " a string "  "  " );    Emergency emergency = (Emergency) wi.getParameter( "  "  " a string "  "  " );
