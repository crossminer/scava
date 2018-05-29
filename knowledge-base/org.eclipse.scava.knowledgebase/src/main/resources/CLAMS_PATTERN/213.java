{
    Reader source;
    final StatefulSession session2;
    List<Message> myList;
    PackageBuilder builder = new PackageBuilder();
    builder.addRuleFlow(source);
    if (!builder.getErrors().isEmpty()) {
        for (DroolsError error: builder.getErrors().getErrors()) {
            // Do something
        }
    }

    Package pkg = builder.getPackage();
    RuleBase ruleBase = RuleBaseFactory.newRuleBase();
    ruleBase.addPackage( pkg );
    final StatefulSession session = ruleBase.newStatefulSession();
    session.setGlobal( " a string " , myList);

    ProcessInstance processInstance = ( ProcessInstance )
                                      session.startProcess( " a string " );
    assertEquals(number, ((InternalProcessRuntime) ((InternalWorkingMemory) session).getProcessRuntime()).getTimerManager().getTimers().size());
    session.halt();

    myList = (List<Message>) session2.getGlobal(  " a string "  );
    processInstance = ( ProcessInstance ) session2.getProcessInstance( processInstance.getId() );

    assertEquals(number, ((InternalProcessRuntime) ((InternalWorkingMemory) session2).getProcessRuntime()).getTimerManager().getTimers().size());

    session2.halt();
    // Do something with myList

    // Do something with processInstance

}    session.setGlobal( "  " a string "  " , myList);                                      session.startProcess( "  " a string "  " );    myList = (List<Message>) session2.getGlobal(  "  " a string "  "  );    session.setGlobal( "  " a string "  " , myList);                                      session.startProcess( "  " a string "  " );    myList = (List<Message>) session2.getGlobal(  "  " a string "  "  );}    session.setGlobal( "  "  " a string "  "  " , myList);                                      session.startProcess( "  "  " a string "  "  " );    myList = (List<Message>) session2.getGlobal(  "  "  " a string "  "  "  );
