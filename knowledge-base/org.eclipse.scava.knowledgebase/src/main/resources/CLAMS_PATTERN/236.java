{
    final StatefulKnowledgeSession ksession;
    SessionPseudoClock clock;
    MeetingStartEvent meetingEvent;
    IncomingCallEvent callEvent;
    WorkingMemoryEntryPoint meetingsEP = ksession.getWorkingMemoryEntryPoint( " a string " );
    WorkingMemoryEntryPoint incomingCallsEP = ksession.getWorkingMemoryEntryPoint( " a string " );

    meetingsEP.insert(meetingEvent);

    clock.advanceTime(number, TimeUnit.MINUTES);
    incomingCallsEP.insert(callEvent);
    ksession.fireAllRules();

    incomingCallsEP.insert(callEvent);

    clock.advanceTime(number, TimeUnit.MINUTES);

    ksession.fireAllRules();

    ksession.dispose();
}    WorkingMemoryEntryPoint meetingsEP = ksession.getWorkingMemoryEntryPoint( "  " a string "  " );    WorkingMemoryEntryPoint incomingCallsEP = ksession.getWorkingMemoryEntryPoint( "  " a string "  " );    WorkingMemoryEntryPoint meetingsEP = ksession.getWorkingMemoryEntryPoint( "  " a string "  " );    WorkingMemoryEntryPoint incomingCallsEP = ksession.getWorkingMemoryEntryPoint( "  " a string "  " );}    WorkingMemoryEntryPoint meetingsEP = ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " );    WorkingMemoryEntryPoint incomingCallsEP = ksession.getWorkingMemoryEntryPoint( "  "  " a string "  "  " );
