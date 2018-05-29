{
    ProcessEvaluator evaluator;
    WorkItem workItem;
    Admission admission;
    TestWorkItemHandler testHandler;
    final String DOMAIN;
    StatefulKnowledgeSession knowledgeSession;
    knowledgeSession.getWorkItemManager().registerWorkItemHandler( " a string " , testHandler);
    knowledgeSession.getWorkItemManager().registerWorkItemHandler( " a string " , testHandler);

    ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN +  " a string " ,
    getProcessParameters(admission));

    assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
    assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " ,  " a string " );
    assertNodeActive(processInstance.getId(), knowledgeSession,  " a string " );

    knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

    assertNodeTriggered(processInstance.getId(),  " a string " );

    if (evaluator.evalRegisterForREG(admission)) {
        assertNodeTriggered(processInstance.getId(),  " a string " );
    } else if (evaluator.evalApologyFromREG(admission)) {
        assertNodeTriggered(processInstance.getId(),  " a string " );
        if (evaluator.evalRegistrationApologyApproval(admission)) {
            assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " );
            assertNodeActive(processInstance.getId(), knowledgeSession,  " a string " );
            assertNodeTriggered(processInstance.getId(),  " a string " );
            if (evaluator.evalRegisterForREG(admission)) {
                assertNodeTriggered(processInstance.getId(),  " a string " );
            } else if (evaluator.evalApologyFromREG(admission)) {
                assertNodeTriggered(processInstance.getId(),  " a string " );
            }
        } else {
            // Do something
        }
    }

    assertNodeTriggered(processInstance.getId(),  " a string " ,  " a string " );
    assertNodeActive(processInstance.getId(), knowledgeSession,  " a string " );

    assertEquals( " a string " , workItem.getName());
    assertEquals( " a string " , workItem.getParameter( " a string " ));
    knowledgeSession.getWorkItemManager().completeWorkItem(workItem.getId(), null);

    assertNodeTriggered(processInstance.getId(),  " a string " );
    assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
}    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN +  "  " a string "  " ,    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );        assertNodeTriggered(processInstance.getId(),  "  " a string "  " );        assertNodeTriggered(processInstance.getId(),  "  " a string "  " );            assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " );            assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );            assertNodeTriggered(processInstance.getId(),  "  " a string "  " );                assertNodeTriggered(processInstance.getId(),  "  " a string "  " );                assertNodeTriggered(processInstance.getId(),  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );    assertEquals( "  " a string "  " , workItem.getName());    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  " a string "  " , testHandler);    ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN +  "  " a string "  " ,    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " ,  "  " a string "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );        assertNodeTriggered(processInstance.getId(),  "  " a string "  " );        assertNodeTriggered(processInstance.getId(),  "  " a string "  " );            assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " );            assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );            assertNodeTriggered(processInstance.getId(),  "  " a string "  " );                assertNodeTriggered(processInstance.getId(),  "  " a string "  " );                assertNodeTriggered(processInstance.getId(),  "  " a string "  " );    assertNodeTriggered(processInstance.getId(),  "  " a string "  " ,  "  " a string "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  " a string "  " );    assertEquals( "  " a string "  " , workItem.getName());    assertEquals( "  " a string "  " , workItem.getParameter( "  " a string "  " ));    assertNodeTriggered(processInstance.getId(),  "  " a string "  " );}    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , testHandler);    knowledgeSession.getWorkItemManager().registerWorkItemHandler( "  "  " a string "  "  " , testHandler);    ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN +  "  "  " a string "  "  " ,    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " ,  "  "  " a string "  "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  "  " a string "  "  " );    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );        assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );        assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );            assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " );            assertNodeActive(processInstance.getId(), knowledgeSession,  "  "  " a string "  "  " );            assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );                assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );                assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " ,  "  "  " a string "  "  " );    assertNodeActive(processInstance.getId(), knowledgeSession,  "  "  " a string "  "  " );    assertEquals( "  "  " a string "  "  " , workItem.getName());    assertEquals( "  "  " a string "  "  " , workItem.getParameter( "  "  " a string "  "  " ));    assertNodeTriggered(processInstance.getId(),  "  "  " a string "  "  " );
