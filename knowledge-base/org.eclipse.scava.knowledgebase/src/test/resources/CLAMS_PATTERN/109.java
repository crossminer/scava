{
    WorkingMemory wm;
    Info.Type type;
    String jobId;
    String workerId;
    QueryResults qrs = wm.getQueryResults( " a string " , new Object[] {
        workerId, jobId, type
    });
    if (qrs.size() == number) {
        // Do something
    }
    QueryResult qr = qrs.get(number);
    if (qr.size() == number) {
        // Do something
    }
    return (Info) qr.get(number);

}    QueryResults qrs = wm.getQueryResults( "  " a string "  " , new Object[] {    QueryResults qrs = wm.getQueryResults( "  " a string "  " , new Object[] {}    QueryResults qrs = wm.getQueryResults( "  "  " a string "  "  " , new Object[] {
